package lv.bootcamp.bartersWeb.authService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private static Logger log = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @ModelAttribute @Valid RegisterRequest request
    ) throws MethodArgumentNotValidException, IncorrectDataException {
        log.info("New user registration " + request.toString());
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @ModelAttribute @Valid AuthenticationRequest request
    ) throws MethodArgumentNotValidException, IncorrectDataException {
        log.info("User logon " + request.toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
