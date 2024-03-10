package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.BasketDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface BasketControllerImpl {
    HttpEntity<?> getBasket();
    HttpEntity<?> addBasket(BasketDto productDto);
    HttpEntity<?> editBasket(UUID id, BasketDto productDto);
    HttpEntity<?> deleteBasket(UUID id);
    HttpEntity<?> getOneBasket(UUID id);
}
