package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserShowDto userToUserShowDto(User user) {

        UserShowDto userShowDto = new UserShowDto();
        userShowDto.setId(user.getId());
        userShowDto.setUsername(user.getUsername());
        userShowDto.setEmail(user.getEmail());
        userShowDto.setRole(user.getRole());
        userShowDto.setDescription(user.getDescription());
        userShowDto.setItems(user.getItems());
        return userShowDto;
    }

    public User userCreateDtoToUser(UserCreateDto userCreateDto) {

        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setPhoneNumber(userCreateDto.getPhoneNumber());
        user.setRole(userCreateDto.getRole());
        user.setEmail(userCreateDto.getEmail());
        user.setDescription(userCreateDto.getDescription());
        user.setImage(userCreateDto.getImage());
        return user;
    }
}
