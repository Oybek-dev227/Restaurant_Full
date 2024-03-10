package Project.Restaurantbackend.implement.controller;

import Project.Restaurantbackend.payload.NewsDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface NewsControllerImpl {
    HttpEntity<?> getNews();
    HttpEntity<?>addNews(NewsDto newsDto);
    HttpEntity<?> editeNews(UUID id, NewsDto newsDto);
    HttpEntity<?> deleteNews(UUID id);
    HttpEntity<?>getOneNews(UUID id);
}
