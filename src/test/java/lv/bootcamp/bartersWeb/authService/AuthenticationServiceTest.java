package lv.bootcamp.bartersWeb.authService;

import lv.bootcamp.bartersWeb.authService.config.JwtService;
import lv.bootcamp.bartersWeb.authService.token.Token;
import lv.bootcamp.bartersWeb.authService.token.TokenRepository;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class AuthenticationServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testRegisterUser() throws IncorrectDataException {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("testuser@barters.web");
        request.setPhoneNumber("+37000000000");

        User savedUser = new User();
        savedUser.setUsername(request.getUsername());
        savedUser.setPassword("encodedpassword");
        savedUser.setEmail(request.getEmail());
        savedUser.setPhoneNumber(request.getPhoneNumber());
        savedUser.setRole(ERole.USER);
        savedUser.setItems(new ArrayList<>());
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedpassword");
        String jwtToken = "generatedjwt";
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn(jwtToken);

        AuthenticationResponse response = authenticationService.registerUser(request);

        Assert.assertNotNull(response);
        assertEquals(response.getAccessToken(), jwtToken);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.anyString());
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(Mockito.any(User.class));
        Mockito.verify(tokenRepository, Mockito.times(1)).save(Mockito.any(Token.class));
    }

    @Test
    void testAuthenticate() throws MethodArgumentNotValidException, IncorrectDataException, AuthenticationException {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("testuser@barters.web");
        user.setPhoneNumber("+37000000000");
        usersRepository.save(user);

        when(usersRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));

        String jwtToken = "jwt-token";
        Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(null);
        authenticationService.saveUserToken(user,jwtToken);
        Mockito.verify(tokenRepository, Mockito.times(1)).save(Mockito.any(Token.class));

        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");

        when(usersRepository.existsByUsername(user.getUsername())).thenReturn(true);
        AuthenticationResponse response = authenticationService.authenticate(request);

        Assertions.assertEquals(jwtToken, response.getAccessToken());
    }

    @Test
    void testAuthenticateWithInvalidRequest() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");
        assertThrows(IncorrectDataException.class, () -> authenticationService.authenticate(request));
    }

    @Test
    void testRegisterWithInvalidRequest() throws IncorrectDataException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("testuser@barters.web");
        user.setPhoneNumber("+37000000000");
        usersRepository.save(user);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("testuser@barters.web");
        request.setPhoneNumber("+37000000000");
        when(usersRepository.existsByUsername(request.getUsername())).thenReturn(true);
        when(usersRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(IncorrectDataException.class, () -> authenticationService.registerUser(request));

        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("newemail@barters.web");
        request.setPhoneNumber("+37000000000");
        when(usersRepository.existsByUsername(request.getUsername())).thenReturn(true);
        assertThrows(IncorrectDataException.class, () -> authenticationService.registerUser(request));

        request.setUsername("newusername");
        request.setPassword("testpassword");
        request.setEmail("testuser@barters.web");
        request.setPhoneNumber("+37000000000");
        when(usersRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(IncorrectDataException.class, () -> authenticationService.registerUser(request));
    }


}