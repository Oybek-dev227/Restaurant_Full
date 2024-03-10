package Project.Restaurantbackend.implement.service;

import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.NewsDto;

import java.util.List;
import java.util.UUID;

public interface NewsServiceImpl {
    List<NewsDto> getNews();

    ApiResponse<?> addNews(NewsDto newsDto);

    ApiResponse<?> editeNews(UUID id, NewsDto newsDto);

    ApiResponse<?> deleteNews(UUID id);
}
