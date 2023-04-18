package lv.bootcamp.bartersWeb.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lv.bootcamp.bartersWeb.entities.ERole;

@Data
@Hidden
public class UserCreateDto {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Incorrect email")
    private String email;
    @NotNull
    @Pattern(regexp = "^([+][3][7][0-2]\\d{8})$",
            message = "Phone number must be 11 digits and start +370 or +371 or +372")
    private String phoneNumber;

    @NotNull
    private ERole role;

    @NotNull
    private String description;

    @NotNull
    private String image;

}
