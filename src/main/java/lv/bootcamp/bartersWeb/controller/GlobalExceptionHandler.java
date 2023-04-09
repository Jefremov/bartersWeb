package lv.bootcamp.bartersWeb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Optional;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        try {
            BindingResult bindingResult = ex.getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            if (!errorMessage.isEmpty() && !fieldName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldName + " " + errorMessage);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
            }
        } catch (Exception e){

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Contact administrator");
    }

}