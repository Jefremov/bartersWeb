package lv.bootcamp.bartersWeb.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidImageValidatorTest {

    @Test
    public void testValidImage() {
        ValidImageValidator validator = new ValidImageValidator();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
        assertTrue(validator.isValid(file, null));
    }

    @Test
    public void testInvalidContentType() {
        ValidImageValidator validator = new ValidImageValidator();
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        assertFalse(validator.isValid(file, null));
    }

    @Test
    public void testNullFile() {
        ValidImageValidator validator = new ValidImageValidator();
        assertTrue(validator.isValid(null, null));
    }

    @Test
    public void testEmptyFile() {
        ValidImageValidator validator = new ValidImageValidator();
        MockMultipartFile file = new MockMultipartFile("file", new byte[0]);
        assertTrue(validator.isValid(file, null));
    }
}
