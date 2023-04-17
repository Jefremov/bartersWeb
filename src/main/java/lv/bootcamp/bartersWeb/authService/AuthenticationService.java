package lv.bootcamp.bartersWeb.authService;

import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.User;
import lombok.RequiredArgsConstructor;
import lv.bootcamp.bartersWeb.authService.config.JwtService;
import lv.bootcamp.bartersWeb.authService.token.ETokenType;
import lv.bootcamp.bartersWeb.authService.token.Token;
import lv.bootcamp.bartersWeb.authService.token.TokenRepository;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    public AuthenticationResponse registerUser(RegisterRequest request) throws IncorrectDataException {
        registerUserExistenceCheck(request);
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
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws MethodArgumentNotValidException, IncorrectDataException {
        loginUserExistenceCheck(request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = usersRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    void saveUserToken(User user, String jwtToken) {
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

    private void registerUserExistenceCheck(RegisterRequest request) throws IncorrectDataException {
        boolean usernameExists = usersRepository.existsByUsername(request.getUsername());
        boolean emailExists = usersRepository.existsByEmail(request.getEmail());
        if (usernameExists && emailExists) {
            log.info("Registering a user with an existing username " + request.getUsername() + " and email " + request.getEmail());
            throw new IncorrectDataException("Username " + request.getUsername() + " and email " + request.getEmail() +
                    " already exist. Choose another username and email");
        }
        if (usernameExists) {
            log.info("Registering a user with an existing username " + request.getUsername());
            throw new IncorrectDataException("Username " + request.getUsername() + " already exist. Choose another username");
        }
        if (emailExists) {
            log.info("Registering a user with an existing email " + request.getEmail());
            throw new IncorrectDataException("Email " + request.getEmail() + " already exist. Choose another email");
        }
    }

    private void loginUserExistenceCheck(AuthenticationRequest request) throws IncorrectDataException {
        if (!usersRepository.existsByUsername(request.getUsername())) {
            throw new IncorrectDataException("Username " + request.getUsername() + " does not exist");
        }
    }

}
