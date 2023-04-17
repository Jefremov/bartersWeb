package lv.bootcamp.bartersWeb.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.services.AdminService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping(path="/admin")

public class AdminController {

    private static Logger log = Logger.getLogger(AdminController.class);
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userinfo/{username}")
    public ResponseEntity<UserShowDto> getUsername(@PathVariable("username") String username) throws IncorrectDataException {
        log.info("Getting info about " + username);
        return adminService.getUserByUsername(username);
    }
}
