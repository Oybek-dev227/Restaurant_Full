package Project.Restaurantbackend.controller;

import Project.Restaurantbackend.entity.News;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.controller.NewsControllerImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.NewsDto;
import Project.Restaurantbackend.repository.NewsRepository;
import Project.Restaurantbackend.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController implements NewsControllerImpl {
    private final NewsService newsService;
    private final NewsRepository newsRepository;

    @Override
    @GetMapping("/list")
    public HttpEntity<?> getNews() {
        List<NewsDto> newsDtoList = newsService.getNews();
        return ResponseEntity.ok(newsDtoList);
    }

    @Override
    @PostMapping
    public HttpEntity<?> addNews(@RequestBody NewsDto newsDto) {
        ApiResponse<?> apiResponse = newsService.addNews(newsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @Override
    @PutMapping("/{id}")
    public HttpEntity<?> editeNews(@PathVariable UUID id, @RequestBody NewsDto newsDto) {
        ApiResponse<?> apiResponse = newsService.editeNews(id, newsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @Override
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteNews(@PathVariable UUID id) {
        ApiResponse<?> apiResponse = newsService.deleteNews(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @Override
    @GetMapping("/{id}")
    public HttpEntity<?> getOneNews(@PathVariable UUID id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getNews", "newsId", id));
        return ResponseEntity.ok(news);
    }

    @PostMapping("/photo/{newsId}")
    public HttpEntity<?> addPhoto(@PathVariable UUID newsId, @RequestParam UUID photoId) {
        ApiResponse<?> apiResponse = newsService.addPhoto(newsId, photoId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
