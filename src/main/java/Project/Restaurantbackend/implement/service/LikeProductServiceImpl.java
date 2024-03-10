package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.LikeProductDto;

import java.util.List;
import java.util.UUID;

public interface LikeProductServiceImpl {
    List<LikeProductDto> getLikeProduct();
    ApiResponse<?> addLikeProduct(LikeProductDto likeProductDto);
    ApiResponse<?> editLikeProduct(UUID id, LikeProductDto likeProductDto);
    ApiResponse<?> deleteLikeProduct(UUID id);
    ApiResponse<?> getOneLikeProduct(UUID id);
}
