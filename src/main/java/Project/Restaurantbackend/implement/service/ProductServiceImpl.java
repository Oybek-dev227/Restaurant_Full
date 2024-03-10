package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductServiceImpl {
    List<ProductDto> getProduct();
    ApiResponse<?> addProduct(ProductDto productDto);
    ApiResponse<?> editProduct(UUID id, ProductDto productDto);
    ApiResponse<?> deleteProduct(UUID id);
}
