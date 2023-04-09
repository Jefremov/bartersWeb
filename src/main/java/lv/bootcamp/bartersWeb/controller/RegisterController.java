package lv.bootcamp.bartersWeb.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
//@Controller

public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto)
            throws MethodArgumentNotValidException, NoSuchMethodException {
        return userService.registerUser(userRegisterDto);
    }




}
