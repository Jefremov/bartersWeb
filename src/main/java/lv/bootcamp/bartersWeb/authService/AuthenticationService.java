package lv.bootcamp.bartersWeb.authService;

import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lv.bootcamp.bartersWeb.authService.config.JwtService;
import lv.bootcamp.bartersWeb.authService.token.ETokenType;
import lv.bootcamp.bartersWeb.authService.token.Token;
import lv.bootcamp.bartersWeb.authService.token.TokenRepository;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static Logger log = Logger.getLogger(AuthenticationService.class);
    private final UsersRepository usersRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegisterRequest request)
            throws MethodArgumentNotValidException, NoSuchMethodException {
        if (usersRepository.existsByUsername(request.getUsername())) {
            log.info("Registering a user with an existing username " + request.getUsername());
            BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
            FieldError fieldError = new FieldError("request", "Username " + request.getUsername(),
                    " already exist. Choose another username");
            bindingResult.addError(fieldError);
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getMethod("registerUser", RegisterRequest.class),
                            0), bindingResult);

        }
        if (usersRepository.existsByEmail(request.getEmail())) {
            log.info("Registering a user with an existing email " + request.getEmail());
            BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
            FieldError fieldError = new FieldError("request", "Email " + request.getEmail(),
                    " already exist. Choose another email");
            bindingResult.addError(fieldError);
            throw new MethodArgumentNotValidException(
                    new MethodParameter(getClass().getMethod("registerUser", RegisterRequest.class),
                            0), bindingResult);
        }
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(ERole.USER)
                .items(new ArrayList<Item>())
                .build();
        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws MethodArgumentNotValidException, NoSuchMethodException {
        if (!usersRepository.existsByUsername(request.getUsername())) {
            log.info("Login a user with an non-existent username " + request.getUsername());
            BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
            FieldError fieldError = new FieldError("request", "Username " + request.getUsername(),
                    " does not exist");
            bindingResult.addError(fieldError);
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getMethod("authenticate", RegisterRequest.class),
                            0), bindingResult);

        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = usersRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(ETokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.usersRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
