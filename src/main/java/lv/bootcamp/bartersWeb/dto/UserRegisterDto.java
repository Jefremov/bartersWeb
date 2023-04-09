package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDto {

    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotNull
    private String description;

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
