package lv.bootcamp.bartersWeb.authService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.bootcamp.bartersWeb.authService.config.JwtService;
import lv.bootcamp.bartersWeb.authService.token.ETokenType;
import lv.bootcamp.bartersWeb.authService.token.Token;
import lv.bootcamp.bartersWeb.authService.token.TokenRepository;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest()
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void testRegisterUser() throws IncorrectDataException {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        request.setEmail("testuser@barters.web");
        request.setPhoneNumber("1234567890");

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
        String refreshToken = "generatedrefresh";
        Mockito.when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn(refreshToken);
        Mockito.when(tokenRepository.save(Mockito.any(Token.class))).thenReturn(null);

        AuthenticationResponse response = authenticationService.registerUser(request);

        Assert.assertNotNull(response);
        assertEquals(response.getAccessToken(), jwtToken);
        assertEquals(response.getRefreshToken(), refreshToken);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.anyString());
        Mockito.verify(jwtService, Mockito.times(1)).generateToken(Mockito.any(User.class));
        Mockito.verify(jwtService, Mockito.times(1)).generateRefreshToken(Mockito.any(User.class));
        Mockito.verify(tokenRepository, Mockito.times(1)).save(Mockito.any(Token.class));
    }




}