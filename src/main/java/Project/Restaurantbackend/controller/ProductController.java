package Project.Restaurantbackend.controller;

import Project.Restaurantbackend.entity.Comment;
import Project.Restaurantbackend.entity.Product;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.controller.ProductControllerImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.ProductDto;
import Project.Restaurantbackend.repository.ProductRepository;
import Project.Restaurantbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController implements ProductControllerImpl {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Override
    @GetMapping
    public HttpEntity<?> getProduct() {
        List<ProductDto> product = productService.getProduct();
        return ResponseEntity.ok(product);
    }


    @GetMapping("/sort-category-by/{id}")
    public HttpEntity<?> getProductsByCategory(@PathVariable Integer id) {
        List<Product> productsByCategory = productRepository.getProductByCategory(id);
        return ResponseEntity.ok(productsByCategory);
    }

    @Override
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody ProductDto productDto) {
        ApiResponse<?> apiResponse = productService.addProduct(productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @Override
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable UUID id, @RequestBody ProductDto productDto) {
        ApiResponse<?> apiResponse = productService.editProduct(id, productDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }


    @Override
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable UUID id) {
        ApiResponse<?> apiResponse = productService.deleteProduct(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @Override
    @GetMapping("/{productId}")
    public HttpEntity<?> getOneProduct(@PathVariable UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
        return ResponseEntity.ok(product);
    }


    @PutMapping("/upload-new-photo/{id}")
    public HttpEntity<?> addNewPhoto(@PathVariable UUID id, @RequestParam(name = "photoId", required = false) UUID photoId) {
        ApiResponse<?> apiResponse = productService.addNewPhoto(id, photoId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PostMapping("/photo/{productId}")
    public HttpEntity<?> addPhoto(@PathVariable UUID productId, @RequestParam UUID photoId) {
        ApiResponse<?> apiResponse = productService.addPhoto(productId, photoId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/sale/product/{id}")
    public HttpEntity<?> saleProduct(@PathVariable UUID id,
                                     @RequestParam(name = "userId", required = false) UUID userId,
                                     @RequestParam(name = "sale-percent", required = false) double salePercent) {
        ApiResponse<?> apiResponse = productService.saleProduct(id, userId, salePercent);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/sale-active/{id}")
    public HttpEntity<?> changeSale(@PathVariable UUID id,
                                    @RequestParam(name = "userId", required = false) UUID userId) {
        ApiResponse<?> apiResponse = productService.saleChange(id, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/sale/product-all")
    public HttpEntity<?> getSaleProduct() {
        List<ProductDto> saleProduct = productService.getSaleProduct();
        return ResponseEntity.ok(saleProduct);
    }


    @PostMapping("/comment")
    public HttpEntity<?> sendComment(@RequestParam(name = "commentId", required = false) UUID commentId,
                                     @RequestParam(name = "productId", required = false) UUID productId) {
        ApiResponse<?> comment = productService.addComment(commentId, productId);
        return ResponseEntity.status(comment.isSuccess() ? 200 : 409).body(comment);
    }

    @GetMapping("/comment/{id}")
    public HttpEntity<?> getCommentByProductId(@PathVariable UUID id) {
        List<Comment> commentByProductId = productService.getCommentByProductId(id);
        return ResponseEntity.ok(commentByProductId);
    }

}

