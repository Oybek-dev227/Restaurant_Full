package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.BasketDto;

import java.util.UUID;

public interface BasketServiceImpl {
    ApiResponse<?> getBasket();
    ApiResponse<?> addBasket(BasketDto basketDto);
    ApiResponse<?> editBasket(UUID id, BasketDto basketDto);
    ApiResponse<?> deleteBasket(UUID id);
    ApiResponse<?> getOneBasket(UUID id);
}
