package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.BuyProductDto;

import java.util.UUID;

public interface BuyProductServiceImpl {
    ApiResponse<?> getBuyProduct();
    ApiResponse<?> addBuyProduct(BuyProductDto buyProductDto);
    ApiResponse<?> editBuyProduct(UUID id, BuyProductDto buyProductDto);
    ApiResponse<?> deleteBuyProduct(UUID id);
    ApiResponse<?> getOneBuyProduct(UUID id);
}
