package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ReviewShowDto;
import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<UserShowDto> getUserByUsername(String username) throws IncorrectDataException;

    ResponseEntity<String> deleteUserByUsername(String username) throws IncorrectDataException;

    ResponseEntity<UserShowDto> setUserAdmin(String username) throws IncorrectDataException;

    ResponseEntity<UserShowDto> createUser(UserCreateDto userCreateDto) throws IncorrectDataException;
}
