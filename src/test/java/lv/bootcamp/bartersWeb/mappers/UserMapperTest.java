package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void userToUserShowDto() {

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("testuser@barters.web");
        user.setRole(ERole.USER);
        user.setDescription("testDescription");
        user.setPhoneNumber("+37000000000");
        user.setImage("testImage");
        user.setItems(new ArrayList<>());
        user.setTokens(new ArrayList<>());

        UserShowDto userShowDto = userMapper.userToUserShowDto(user);

        assertEquals(user.getId(), userShowDto.getId());
        assertEquals(user.getUsername(), userShowDto.getUsername());
        assertEquals(user.getRole(), userShowDto.getRole());
        assertEquals(user.getEmail(), userShowDto.getEmail());
        assertEquals(user.getDescription(), userShowDto.getDescription());
        assertEquals(user.getEmail(), userShowDto.getEmail());
        assertEquals(user.getPhoneNumber(), userShowDto.getPhoneNumber());
        assertEquals(user.getItems(), userShowDto.getItems());

    }

    @Test
    void userCreateDtoToUser() {

        UserCreateDto userCreateDto = new UserCreateDto();

        userCreateDto.setUsername("testuser");
        userCreateDto.setPassword("testpassword");
        userCreateDto.setEmail("testuser@barters.web");
        userCreateDto.setRole(ERole.USER);
        userCreateDto.setDescription("testDescription");
        userCreateDto.setPhoneNumber("+37000000000");
        userCreateDto.setImage("testImage");

        User user = userMapper.userCreateDtoToUser(userCreateDto);

        assertEquals(userCreateDto.getUsername(), user.getUsername());
        assertEquals(userCreateDto.getRole(), user.getRole());
        assertEquals(userCreateDto.getEmail(), user.getEmail());
        assertEquals(userCreateDto.getDescription(), user.getDescription());
        assertEquals(userCreateDto.getEmail(), user.getEmail());
        assertEquals(userCreateDto.getPhoneNumber(), user.getPhoneNumber());

    }
}