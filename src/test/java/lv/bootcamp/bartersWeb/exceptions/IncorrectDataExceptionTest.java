package lv.bootcamp.bartersWeb.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncorrectDataExceptionTest {

    @Test
    public void testConstructor() {
        String errorMessage = "Test error message";
        IncorrectDataException exception = new IncorrectDataException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
