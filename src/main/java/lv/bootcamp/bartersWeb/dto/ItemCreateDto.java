package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lv.bootcamp.bartersWeb.exceptions.ValidImage;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemCreateDto {

    @NotBlank(message = "Title must be filled in")
    private String title;

    @ValidImage(message = "Invalid image type")
    private MultipartFile file;

    @NotBlank(message = "Title must be filled in")
    private String description;

    private boolean imageChange;

    private String category;

    @NotBlank(message = "State must be filled in")
    private String state;

    private String status;

    private String username;

}
