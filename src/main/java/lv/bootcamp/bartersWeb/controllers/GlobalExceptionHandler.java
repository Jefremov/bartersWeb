package lv.bootcamp.bartersWeb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.apache.log4j.Logger;


@ControllerAdvice
class GlobalExceptionHandler {

    private static Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        try {
            BindingResult bindingResult = ex.getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            if (!errorMessage.isEmpty() && !fieldName.isEmpty()) {
                log.info(fieldName + " " + errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldName + " " + errorMessage);
            } else {
                log.error(fieldName + " " + errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
            }
        } catch (Exception e) {
            log.error("An error occurred " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
        }
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<Object> handleNoSuchMethodException(NoSuchMethodException ex) {

        try {
            log.info("An error occurred " + ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
        } catch (Exception e) {
            log.error("An error occurred " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
        }
    }

}