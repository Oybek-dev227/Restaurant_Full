package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.ProductDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface ProductControllerImpl {
    HttpEntity<?> getProduct();
    HttpEntity<?> addProduct(ProductDto productDto);
    HttpEntity<?> editProduct(UUID id, ProductDto productDto);
    HttpEntity<?> deleteProduct(UUID id);
    HttpEntity<?> getOneProduct(UUID id);
//    HttpEntity<?>getProductPagination(Integer id);
}
