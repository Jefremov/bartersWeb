package lv.bootcamp.bartersWeb.dto;

import jakarta.servlet.annotation.HandlesTypes;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.ContentType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemCreateDto {
    @NotBlank(message = "Title must be filled in")
    private String title;
    private MultipartFile file;
    @NotBlank(message = "Title must be filled in")
    private String description;
    private String category;
    @NotBlank(message = "State must be filled in")
    private String state;
    private String status;
    private Long userId;
}
