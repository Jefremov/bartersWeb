package lv.bootcamp.bartersWeb.authService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Home", description = "Users registration and authentication")
public class AuthenticationController {

    private static Logger log = Logger.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "User registration and autologin")
    public ResponseEntity<AuthenticationResponse> register(
            @ModelAttribute @Valid RegisterRequest request
    ) throws MethodArgumentNotValidException, IncorrectDataException {
        log.info("New user registration " + request.toString());
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }
    @PostMapping("/login")
    @Operation(summary = "User logging")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @ModelAttribute @Valid AuthenticationRequest request
    ) throws MethodArgumentNotValidException, IncorrectDataException {
        log.info("User registration " + request.toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @Operation
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
