package lv.bootcamp.bartersWeb.authService;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User registration form")
public class RegisterRequest {

    @NotBlank
    @Schema(description = "Uniq username")
    private String username;
    @NotBlank
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Incorrect email")
    @Schema(description = "Uniq email")
    private String email;
    @NotNull
    @Pattern(regexp = "^([+][3][7][0-2]\\d{8})$",
            message = "Phone number must be 11 digits and start +370 or +371 or +372")
    private String phoneNumber;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}