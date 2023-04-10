package lv.bootcamp.bartersWeb.controllers;


import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Object> registerUser(@Validated @RequestBody UserRegisterDto userRegisterDto){
        return userService.registerUser(userRegisterDto);
    }


}
