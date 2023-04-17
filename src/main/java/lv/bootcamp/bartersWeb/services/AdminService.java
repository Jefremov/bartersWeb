package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<UserShowDto> getUserByUsername(String username) throws IncorrectDataException;
}
