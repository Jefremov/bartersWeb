package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entities.User;
import org.springframework.stereotype.Component;

import static lv.bootcamp.bartersWeb.entities.ERole.USER;

@Component
public class UserMapper {

    public User userRegisterDtoToUser(UserRegisterDto userDto){

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setDescription(userDto.getDescription());
        user.setRole(USER);
        return user;

    }

}
