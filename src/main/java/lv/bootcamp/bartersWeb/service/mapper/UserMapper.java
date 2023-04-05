package lv.bootcamp.bartersWeb.service.mapper;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entity.ERole;
import lv.bootcamp.bartersWeb.entity.UserEntity;
import lv.bootcamp.bartersWeb.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity userRegisterDtoToUser(UserRegisterDto userDto){

        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDescription(user.getDescription());
        user.setRole(ERole.ROLE_USER);
        return user;

    }

}
