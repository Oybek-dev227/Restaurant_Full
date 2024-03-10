    package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.BuyProductDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

    public interface BuyProductControllerImpl {
        HttpEntity<?> getBuyProduct();
        HttpEntity<?> addBuyProduct(BuyProductDto buyProductDto);
        HttpEntity<?> editBuyProduct(UUID id, BuyProductDto buyProductDto);
        HttpEntity<?> deleteBuyProduct(UUID id);
        HttpEntity<?> getOneBuyProduct(UUID id);
    }
