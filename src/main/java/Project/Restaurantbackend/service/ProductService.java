package Project.Restaurantbackend.service;

import Project.Restaurantbackend.entity.*;
import Project.Restaurantbackend.entity.enums.RoleName;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.service.ProductServiceImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.ProductDto;
import Project.Restaurantbackend.repository.*;
import Project.Restaurantbackend.repository.res.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuthRepository authRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<ProductDto> getProduct() {
        List<Product> all = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : all) {
            ProductDto productDto = ProductDto.builder().id(product.getId()).category(product.getCategory()).isBasket(product.isBasket()).isLike(product.isLike()).stars(product.getStars()).name(product.getName()).price(product.getPrice()).sale(product.isSale()).photoId(product.getPhotos().get(0).getPhotoId()).description(product.getDescription()).build();
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public ApiResponse<?> addProduct(ProductDto productDto) {
        try {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException(404, "getCategory", "productDto", productDto));
            if (category != null) {
                boolean b = productRepository.existsProductByName(productDto.getName());
                if (!b) {
                    Photo photo = Photo.builder().photoId(productDto.getPhotoId()).build();
                    Photo save = photoRepository.save(photo);
                    Product product = Product.builder()
                            .category(category)
                            .name(productDto.getName())
                            .price(productDto.getPrice())
                            .description(productDto.getDescription())
                            .photoId(productDto.getPhotoId())
                            .photos(Collections.singletonList(save))
                            .sale(false)
                            .salePercent(0)
                            .build();
                    productRepository.save(product);
                    return new ApiResponse<>("Maxsulot saqlandi", true);
                }
                return new ApiResponse<>("bunday maxsulot bizda avvaldan mavjud", false);
            }
            return new ApiResponse<>("Bunday bo'lim mavjud emas", false);
        } catch (Exception e) {
            return new ApiResponse<>("Mahsulot saqlashda xatolik", false);
        }
    }

    @Override
    public ApiResponse<?> editProduct(UUID id, ProductDto productDto) {
        try {
            Optional<Product> byId = productRepository.findById(id);
            if (byId.isPresent()) {
                Product product = byId.get();
                String malumot = productDto.getMalumot();
                if (malumot != null) {
                    switch (malumot) {
                        case "name" -> product.setName(productDto.getName());
                        case "price" -> product.setPrice(productDto.getPrice());
                        case "description" -> product.setDescription(productDto.getDescription());
                        case "image" -> product.setPhotoId(productDto.getPhotoId());
                        default -> {
                            return new ApiResponse<>("Ma'lumot tug'ri kelmadi", false);
                        }
                    }
                    productRepository.save(product);
                    return new ApiResponse<>("tahrirlandi", true);
                } else {
                    return new ApiResponse<>("Ma'lumot bo'sh bo'lmasin", false);
                }
            } else {
                return new ApiResponse<>("Bunday productyo'q", false);
            }
        } catch (Exception e) {
            return new ApiResponse<>("Mahsulot taxrirlashda xatolik", false);
        }
    }


    @Override
    public ApiResponse<?> deleteProduct(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "id", id));
        productRepository.delete(product);
        return new ApiResponse<>("maxsulot o'chirib tashlandi", true);
    }

    public ApiResponse<?> addPhoto(UUID id, UUID photoId) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "id", id));
            if (product.getPhotoId() == null) {
                product.setPhotoId(photoId);
                productRepository.save(product);
                return new ApiResponse<>("rasm saqlandi", true);
            } else {
                AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(product.getPhotoId());
                Attachment attachment = attachmentRepository.findById(product.getPhotoId()).orElseThrow(() -> new ResourceNotFoundException(404, "getPhotoId", "photoId", photoId));
                product.setPhotoId(photoId);
                Photo photo = new Photo(photoId);
                Photo saveP = photoRepository.save(photo);
                product.getPhotos().remove(photoRepository.findPhotoByPhotoId(attachment.getId()));
                attachmentContentRepository.delete(byAttachmentId);
                attachmentRepository.delete(attachment);
                product.getPhotos().add(0, saveP);
                productRepository.save(product);
                return new ApiResponse<>("saqlandi", true);
            }
        } catch (Exception e) {
            return new ApiResponse<>("Xatolik", false);
        }
    }

    public ApiResponse<?> addNewPhoto(UUID id, UUID photoId) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getProductId", "id", id));
            if (photoId != null) {
                Photo save = photoRepository.save(new Photo(photoId));
                product.getPhotos().add(save);
                productRepository.save(product);
                return new ApiResponse<>("rasm saqlandi", true);
            }
            return new ApiResponse<>("rasm saqlashda hatolik", false);
        } catch (Exception e) {
            return new ApiResponse<>("xatolik", false);
        }
    }

    public ApiResponse<?> saleProduct(UUID productId, UUID userId, double salePercent) {
        User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "userId", userId));
        if (getRole(user.getRoles())) {
            if (salePercent > 0) {
                Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
                double percentPrice = (product.getPrice() * salePercent) / 100;
                product.setPrice(product.getPrice());
                product.setSalePercent(salePercent);
                product.setSalePrice(product.getPrice() - percentPrice);
                product.setSale(true);
                productRepository.save(product);
                return new ApiResponse<>("chegirma joylandi", true);
            }
            return new ApiResponse<>("Cegirma qushish foiz kiriting", false);
        }
        return new ApiResponse<>("sizga bunday xuquq berilmagan", false);
    }

    public ApiResponse<?> saleChange(UUID productId, UUID userId) {
        User user = authRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "userId", userId));
        if (getRole(user.getRoles())) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
            product.setSale(false);
            product.setSalePercent(0);
            product.setSalePrice(0);
            productRepository.save(product);
            return new ApiResponse<>("sale olib tashlandi", true);
        }
        return new ApiResponse<>("sizga hunday xuquq berilmagan", false);

    }

    public List<ProductDto> getSaleProduct() {
        List<Product> all = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : all) {
            if (product.isSale()) {
                ProductDto prduct = ProductDto.builder()
                        .id(product.getId())
                        .category(product.getCategory())
                        .isBasket(product.isBasket())
                        .isLike(product.isLike())
                        .stars(product.getStars())
                        .name(product.getName())
                        .price(product.getPrice())
                        .sale(product.isSale())
                        .salePercent(product.getSalePercent())
                        .photoId(product.getPhotos().get(0).getPhotoId())
                        .description(product.getDescription())
                        .build();
                productDtoList.add(prduct);
            }
        }
        return productDtoList;
    }

    public ApiResponse<?> addComment(UUID commentId, UUID productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(404, "getComment", "commentId", commentId));
            product.getComments().add(comment);
            productRepository.save(product);
            return new ApiResponse<>("Mallades", true);
        } catch (Exception e) {
            return new ApiResponse<>("Comment saqlashda xatolik", false);
        }
    }

    public List<Comment> getCommentByProductId(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", id));
        List<Comment> resComment = new ArrayList<>();
        List<Comment> comments = product.getComments();
        for (int i = comments.size() - 1; i >= 0; i--) {
//            Comment comment = Comment.builder()
//                    .user(comments.get(i).getUser())
//                    .message(comments.get(i).getMessage())
//                    .date(String.valueOf(comments.get(i).getCreatedAt()))
//                    .build();
//            comment.setId(comments.get(i).getId());
//            comment.setCreatedAt(comments.get(i).getCreatedAt());
//            comment.setUpdatedAt(comments.get(i).getUpdatedAt());
            resComment.add(comments.get(i));
        }
        return resComment;
    }


    public boolean getRole(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.ADMIN)) {
                return true;
            }
        }
        return false;
    }

}
