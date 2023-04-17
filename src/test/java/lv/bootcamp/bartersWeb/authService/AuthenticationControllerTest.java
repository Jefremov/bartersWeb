package lv.bootcamp.bartersWeb.authService;

import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;


    @Test
    void register() throws IncorrectDataException, MethodArgumentNotValidException {

        final RegisterRequest request = mock(RegisterRequest.class);
        final ResponseEntity response = mock(ResponseEntity.class);
        when(authenticationController.register(request)).thenReturn(response);
    }

    @Test
    void authenticate() {
    }
}