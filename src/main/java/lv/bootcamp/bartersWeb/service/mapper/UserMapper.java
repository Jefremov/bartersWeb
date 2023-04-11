package lv.bootcamp.bartersWeb.service.mapper;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entity.ERole;
import lv.bootcamp.bartersWeb.entity.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userRegisterDtoToUser(UserRegisterDto userDto){

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setDescription(user.getDescription());
        user.setRole(ERole.ROLE_USER);
        return user;

    }

}
