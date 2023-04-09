package lv.bootcamp.bartersWeb.controller;

import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    private static Logger log = Logger.getLogger(RegisterController.class);
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto)
            throws MethodArgumentNotValidException, NoSuchMethodException {
        log.info("New user registration " + userRegisterDto.toString());
        return userService.registerUser(userRegisterDto);
    }
}
