package Project.Restaurantbackend.payload;

import Project.Restaurantbackend.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiResponse<T> implements Serializable {
    private String message;
    private boolean success;
    private UUID comId;
    private ResToken resToken;
    private User user;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, UUID comId) {
        this.message = message;
        this.success = success;
        this.comId = comId;
    }
}