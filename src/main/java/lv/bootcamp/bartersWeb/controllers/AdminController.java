package lv.bootcamp.bartersWeb.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.services.AdminService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Hidden
@RestController
@RequestMapping(path = "/admin")
@PreAuthorize("hasRole = ('ADMIN')")
public class AdminController {

    private static Logger log = Logger.getLogger(AdminController.class);
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user/info/{username}")
    public ResponseEntity<UserShowDto> getUserByUsername(@PathVariable("username") String username)
            throws IncorrectDataException {
        log.info("Getting info about " + username);
        return adminService.getUserByUsername(username);
    }

    @DeleteMapping("/user/delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username)
            throws IncorrectDataException {
        return adminService.deleteUserByUsername(username);
    }

    @PutMapping("/user/setAdmin/{username}")
    public ResponseEntity<UserShowDto> setUserAdmin(@PathVariable String username)
            throws IncorrectDataException {
        return adminService.setUserAdmin(username);
    }

    @PostMapping("/user/new")
    public ResponseEntity<UserShowDto> addUser(@ModelAttribute @Valid UserCreateDto userCreateDto)
            throws IncorrectDataException {
        return adminService.createUser(userCreateDto);
    }




}
