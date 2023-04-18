package lv.bootcamp.bartersWeb.exceptions;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lv.bootcamp.bartersWeb.pojo.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private MockHttpServletRequest request;

    private ValidatorFactory validatorFactory;

    private Validator validator;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void testHandleNoSuchMethodException() {
        NoSuchMethodException ex = new NoSuchMethodException();
        ResponseEntity<ResponseMessage> response = exceptionHandler.handleNoSuchMethodException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Internal error. Contact administrator", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void testHandleIncorrectDataException() {
        IncorrectDataException ex = new IncorrectDataException("Incorrect data");
        ResponseEntity<ResponseMessage> response = exceptionHandler.handleIncorrectDataException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Incorrect data", Objects.requireNonNull(response.getBody()).getMessage());
    }

}
