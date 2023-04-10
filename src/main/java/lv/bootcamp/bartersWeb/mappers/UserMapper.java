package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.User;

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
