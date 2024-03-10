package Project.Restaurantbackend.service;

import Project.Restaurantbackend.entity.Attachment;
import Project.Restaurantbackend.entity.AttachmentContent;
import Project.Restaurantbackend.entity.News;
import Project.Restaurantbackend.exception.ResourceNotFoundException;
import Project.Restaurantbackend.implement.service.NewsServiceImpl;
import Project.Restaurantbackend.payload.ApiResponse;
import Project.Restaurantbackend.payload.NewsDto;
import Project.Restaurantbackend.repository.AttachmentContentRepository;
import Project.Restaurantbackend.repository.AttachmentRepository;
import Project.Restaurantbackend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService implements NewsServiceImpl {
    private final NewsRepository newsRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public List<NewsDto> getNews() {
        List<News> all = newsRepository.findAll();
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : all) {
            NewsDto newsDto = NewsDto.builder()
                    .id(news.getId())
                    .photoId(news.getPhotoId())
                    .name(news.getName())
                    .description(news.getDescription())
                    .build();
            newsDtos.add(newsDto);
        }
        return newsDtos;
    }

    @Override
    public ApiResponse<?> addNews(NewsDto newsDto) {
        try {
            News news = News.builder()
                    .photoId(newsDto.getPhotoId())
                    .name(newsDto.getName())
                    .description(newsDto.getDescription())
                    .build();
            newsRepository.save(news);
            return new ApiResponse<>("Yangilik saqlandi", true);
        } catch (Exception e) {
            return new ApiResponse<>("xatolik", false);
        }
    }

    @Override
    public ApiResponse<?> editeNews(UUID id, NewsDto newsDto) {
        try {
            News news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getNews", "id", id));
            news.setName(newsDto.getName());
            news.setDescription(newsDto.getDescription());
            newsRepository.save(news);
            return new ApiResponse<>("Yangilik taxrirlandi", true);
        } catch (Exception e) {
            return new ApiResponse<>("Xatolik", false);
        }
    }

    @Override
    public ApiResponse<?> deleteNews(UUID id) {
        try {
            News news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404, "getNews", "id", id));
            newsRepository.delete(news);
            return new ApiResponse<>("Yangilik o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse<>("xatolik", false);
        }
    }

    public ApiResponse<?> addPhoto(UUID newsId, UUID photoId) {
        try {
            News news = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException(404, "getNewsId", "newsId", newsId));
            if (news.getPhotoId() == null) {
                news.setPhotoId(photoId);
                newsRepository.save(news);
                return new ApiResponse<>("saqlandi", true);
            } else {
                AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(news.getPhotoId());
                Attachment attachment = attachmentRepository.findById(news.getPhotoId()).orElseThrow(() -> new ResourceNotFoundException(404, "getPhotoId", "photoId", photoId));
                attachmentContentRepository.delete(byAttachmentId);
                attachmentRepository.delete(attachment);
                news.setPhotoId(photoId);
                newsRepository.save(news);
                return new ApiResponse<>("saqlandi", true);
            }
        } catch (Exception e) {

            return new ApiResponse<>("Xatolik", false);
        }
    }
}
