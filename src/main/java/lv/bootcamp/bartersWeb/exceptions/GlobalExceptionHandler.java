package lv.bootcamp.bartersWeb.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import lv.bootcamp.bartersWeb.pojo.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
class GlobalExceptionHandler {

    private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);
    @Data
    public class Violation {
        private final String fieldName;
        private final String message;
    }
    @Data
    public class ValidationErrorResponse { private List<Violation> violations = new ArrayList<>();}

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//
//        try {
//            BindingResult bindingResult = ex.getBindingResult();
//            FieldError fieldError = bindingResult.getFieldError();
//            String fieldName = fieldError.getField();
//            String errorMessage = fieldError.getDefaultMessage();
//            if (!errorMessage.isEmpty() && !fieldName.isEmpty()) {
//                log.info(fieldName + " " + errorMessage);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(fieldName + " " + errorMessage));
//            } else {
//                log.error(fieldName + " " + errorMessage);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Internal error. Contact administrator"));
//            }
//        } catch (Exception e) {
//            log.error("An error occurred " + e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Internal error. Contact administrator"));
//        }
//    }
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<ResponseMessage> handleNoSuchMethodException(NoSuchMethodException ex) {

        try {
            log.info("An error occurred " + ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Internal error. Contact administrator"));
        } catch (Exception e) {
            log.error("An error occurred " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Internal error. Contact administrator"));
        }
    }
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ResponseMessage> handleIncorrectDataException(IncorrectDataException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(exception.getMessage()));
    }

}