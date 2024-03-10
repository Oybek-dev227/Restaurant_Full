package Project.Restaurantbackend.controller;

import Project.Restaurantbackend.entity.LikeProduct;
import Project.Restaurantbackend.entity.Product;
import Project.Restaurantbackend.entity.User;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.payload.*;
import Project.Restaurantbackend.repository.AuthRepository;
import Project.Restaurantbackend.repository.LikeProductRepository;
import Project.Restaurantbackend.repository.ProductRepository;
import Project.Restaurantbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final ProductRepository productRepository;
    private final LikeProductRepository likeProductRepository;

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto request) {
        return authService.login(request, authenticationManager);
    }

//    @GetMapping("/list")
//    public HttpEntity<?> getUser() {
//        List<RegisterDto> user = authService.getUser();
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/register")
    public HttpEntity<?> addUser(@RequestBody RegisterDto registerDto) {
        return authService.addUser(registerDto, authenticationManager);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id) {
        ApiResponse<?> apiResponse = authService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable UUID id, @RequestBody RegisterDto registerDto) {
        ApiResponse<?> apiResponse = authService.editUser(id, registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneUser(@PathVariable UUID id) {
        try {
            User user = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "get user", "id", id));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @PostMapping("/photo/{id}")
    public HttpEntity<?> addPhoto(@PathVariable UUID id, @RequestParam UUID photoId) {
        ApiResponse<?> apiResponse = authService.addPhoto(id, photoId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-user-basket/{id}")
    private HttpEntity<?> getBasket(@PathVariable UUID id){
        List<BasketDto> basketUser = authService.getBasketUser(id);
        return ResponseEntity.ok(basketUser);
    }

    @GetMapping("/get-isBasket-product/{id}")
    private HttpEntity<?> isBasketProduct(@PathVariable UUID id, @RequestParam(name = "productId", required = false) UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
        User user = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "id", id));
        for (Product basketProduct : user.getBasket().getProducts()) {
            if (product.equals(basketProduct)) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/user-basket/{id}")
    private HttpEntity<?> Basket(@PathVariable UUID id, @RequestBody BasketDto basketDto){
        ApiResponse<?> basketProduct = authService.basketProduct(id, basketDto);
        return ResponseEntity.status(basketProduct.isSuccess() ? 200 : 409).body(basketProduct);
    }

    @GetMapping("/get-like-product/{id}")
    private HttpEntity<?> getLikeProduct(@PathVariable UUID id) {
        List<LikeProductDto> likeProduct = authService.getLikeProduct(id);
        return ResponseEntity.ok(likeProduct);
    }

    @GetMapping("/get-isLike-product/{id}")
    private HttpEntity<?> isLikeProduct(@PathVariable UUID id, @RequestParam(name = "productId", required = false) UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(404, "getProduct", "productId", productId));
        User user = authRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getUser", "id", id));
        for (Product likeProduct : user.getLikeProduct().getProducts()) {
            if (product.equals(likeProduct)) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/like-product/{id}")
    private HttpEntity<?> likeProduct(@PathVariable UUID id, @RequestBody LikeProductDto likeProductDto) {
        ApiResponse<?> addLikeProduct = authService.addLikeProduct(id, likeProductDto);
        return ResponseEntity.status(addLikeProduct.isSuccess() ? 200 : 409).body(addLikeProduct);
    }
}
