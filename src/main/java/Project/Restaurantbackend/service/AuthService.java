package Project.Restaurantbackend.service;

import Project.Restaurantbackend.entity.*;
import Project.Restaurantbackend.payload.*;
import Project.Restaurantbackend.repository.*;
import Project.Restaurantbackend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;
    private final LikeProductRepository likeProductRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;


    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return (UserDetails) authRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("getUser"));
    }

    public UserDetails getUserById(UUID id) {
        return (UserDetails) authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
    }

    public HttpEntity<?> login(LoginDto request, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );
        User user = authRepository.findUserByPhoneNumber(request.getPhoneNumber()).orElseThrow(() -> new ResourceNotFoundException("getUser"));
        ResToken resToken = new ResToken(generateToken(request.getPhoneNumber()));
        System.out.println(ResponseEntity.ok(getMal(user, resToken)));
        return ResponseEntity.ok(getMal(user, resToken));
    }

    public GetData getMal(User user, ResToken resToken) {
        return new GetData(user, resToken);
    }

    private String generateToken(String phoneNumber) {
        User user = authRepository.findUserByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("getUser"));
        return jwtTokenProvider.generateToken(user.getId());
    }


    public HttpEntity<?> addUser(RegisterDto registerDto, AuthenticationManager authenticationManager) {
        try {
            boolean existsUserByPhoneNumber = authRepository.existsUserByPhoneNumber(registerDto.getPhoneNumber());
            if (!existsUserByPhoneNumber) {
                User user = User.builder()
                        .name(registerDto.getName())
                        .surname(registerDto.getSurname())
                        .email(registerDto.getEmail())
                        .phoneNumber(registerDto.getPhoneNumber())
                        .password(passwordEncoder().encode(registerDto.getPassword()))
                        .roles(Collections.singleton(roleRepository.findById(2).orElseThrow(() -> new ResourceNotFoundException("getRole"))))
                        .photoId(registerDto.getPhotoId())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build();
                User save = authRepository.save(user);
                LoginDto loginDto = LoginDto.builder()
                        .phoneNumber(save.getPhoneNumber())
                        .password(registerDto.getPassword())
                        .build();
                return login(loginDto, authenticationManager);
            } else {
                return ResponseEntity.ok(new ApiResponse<>("Afsuski bunday telefon raqamdan avval foydalanilgan😞", false));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse<>("Xatolik", false));
        }
    }


    public List<LikeProductDto> getLikeProduct(UUID userId) {
        User user = authRepository.findById(userId).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getUser", "userId", userId));
        List<LikeProductDto> likeProductDtoList = new ArrayList<>();
        LikeProductDto likeProductDto = LikeProductDto.builder()
                .products(user.getLikeProduct().getProducts())
                .build();
        likeProductDtoList.add(likeProductDto);
        return likeProductDtoList;
    }

    public ApiResponse<?> addLikeProduct(UUID userId, LikeProductDto likeProductDto) {
        try {
            User user = authRepository.findById(userId).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getUser", "userId", userId));
            Product product = productRepository.findById(likeProductDto.getProductId()).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getProduct", "productId", likeProductDto.getProductId()));
            if (user.getLikeProduct() == null) {
                LikeProduct likeProduct = LikeProduct.builder()
                        .products(Collections.singletonList(product))
                        .build();
                likeProductRepository.save(likeProduct);
                user.setLikeProduct(likeProduct);
                authRepository.save(user);
                return new ApiResponse<>("Mahsulot sevimlilarga saqlandi", true);
            } else {
                for (Product likeProduct : user.getLikeProduct().getProducts()) {
                    if (likeProduct.getId().equals(product.getId())) {
                        user.getLikeProduct().getProducts().remove(likeProduct);
                        authRepository.save(user);
                        return new ApiResponse<>("Mahsulot sevimlilardan olib tashlandi", true);
                    }
                }
                user.getLikeProduct().getProducts().add(product);
                authRepository.save(user);
                return new ApiResponse<>("Mahsulot sevimlilarga saqlandi", true);
            }
        } catch (Exception e) {
            return new ApiResponse<>("Like productda xatolik", false);
        }
    }

    public List<BasketDto> getBasketUser(UUID userId) {
        User user = authRepository.findById(userId).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "user", "id", userId));
        List<BasketDto> basketDtos = new ArrayList<>();
        BasketDto basketDto = BasketDto.builder()
                .products(user.getBasket().getProducts())
                .build();
        basketDtos.add(basketDto);
        return basketDtos;
    }

    public ApiResponse<?> basketProduct(UUID userId, BasketDto basketDto) {
        try {
            User user = authRepository.findById(userId).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "user", "id", userId));
            Product product = productRepository.findById(basketDto.getProductId()).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getProduct", "productId", basketDto.getProductId()));
            if (user.getBasket() == null) {
                Basket basket = Basket.builder()
                        .products(Collections.singletonList(product))
                        .build();
                basketRepository.save(basket);
                user.setBasket(basket);
                authRepository.save(user);
                return new ApiResponse<>("Mahsulot savatga saqlandi", true);
            } else {
                for (Product basketProduct : user.getBasket().getProducts()) {
                    if (basketProduct.getId().equals(product.getId())) {
                        user.getBasket().getProducts().remove(basketProduct);
                        authRepository.save(user);
                        return new ApiResponse<>("Mahsulot savatdan olindi", true);
                    }
                }
                user.getBasket().getProducts().add(product);
                authRepository.save(user);
                return new ApiResponse<>("Mahsulot savatga saqlandi", true);
            }
        } catch (Exception e) {
            System.err.println("Xato" + e);
            return new ApiResponse<>("Savatga salashda xatolik" + e, false);
        }
    }

//    public List<RegisterDto> getUser() {
//        List<User> all = authRepository.findAll();
//        List<RegisterDto> registerDtoList = new ArrayList<>();
//        for (User user : all) {
//            registerDtoList.add(
//                    RegisterDto.builder()
//                            .id(user.getId())
//                            .roles(user.getRoles())
//                            .name(user.getName())
//                            .surname(user.getSurname())
//                            .phoneNumber(user.getPhoneNumber())
//                            .email(user.getEmail())
//                            .password(user.getPassword())
//                            .photoId(user.getPhotoId())
//                            .build()
//            );
//        }
//        return registerDtoList;
//    }

    public ApiResponse<?> deleteUser(UUID id) {
        try {
            User getUser = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
            authRepository.delete(getUser);
            return new ApiResponse<>("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse<>("Xatolik", false);
        }
    }

    public ApiResponse<?> editUser(UUID id, RegisterDto registerDto) {
        try {
            User getUser = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
            getUser.setName(registerDto.getName());
            getUser.setSurname(registerDto.getSurname());
            getUser.setPhoneNumber(registerDto.getPhoneNumber());
            getUser.setEmail(registerDto.getEmail());
            getUser.setPassword(passwordEncoder().encode(registerDto.getPassword()));
            authRepository.save(getUser);
            return new ApiResponse<>("Taxrirlandi", true);
        } catch (Exception e) {
            return new ApiResponse<>("Xatolik", false);
        }
    }

    public ApiResponse<?> addPhoto(UUID id, UUID photoId) {
        try {
            User partner = authRepository.findById(id).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getUser", "user id", id));
            if (partner.getPhotoId() == null) {
                partner.setPhotoId(photoId);
                authRepository.save(partner);
                return new ApiResponse<>("saqlandi", true);
            } else {
                AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(partner.getPhotoId());
                Attachment attachment = attachmentRepository.findById(partner.getPhotoId()).orElseThrow(() -> new Project.Restaurantbackend.exception.ResourceNotFoundException(404, "getPhotoId", "photoId", photoId));
                attachmentContentRepository.delete(byAttachmentId);
                attachmentRepository.delete(attachment);
                partner.setPhotoId(photoId);
                authRepository.save(partner);
                return new ApiResponse<>("saqlandi", true);
            }
        } catch (Exception e) {

            return new ApiResponse<>("Xatolik", false);
        }
    }
}
