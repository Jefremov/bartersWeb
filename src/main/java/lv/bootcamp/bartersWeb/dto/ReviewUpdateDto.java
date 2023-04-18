package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewUpdateDto {
    @NotBlank(message = "must be filled")
    private String grade;

    @Size(max = 500, message = "must be less than 500 characters")
    @Size(min = 5, message = "must consist of at least 5 characters")
    private String comment;
}
