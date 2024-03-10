package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.LikeProductDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface LikeControllerImpl {
    HttpEntity<?> getLikeProduct();
    HttpEntity<?> addLikeProduct(LikeProductDto likeProductDto);
    HttpEntity<?> editLikeProduct(UUID id, LikeProductDto likeProductDto);
    HttpEntity<?> deleteLikeProduct(UUID id);
    HttpEntity<?> getOneLikeProduct(UUID id);
}
