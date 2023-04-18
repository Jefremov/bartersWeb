package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.mappers.UserMapper;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class AdminServiceImplTest {

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    UserMapper userMapper;


    @Test
    void testGetUserByUsername() throws Exception {

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
        usersRepository.save(user);

        when(usersRepository.findUserByUsername("testuser")).thenReturn(user);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        when(usersRepository.existsByUsername(user.getUsername())).thenReturn(true);

        ResponseEntity<UserShowDto> response = adminService.getUserByUsername("testuser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserShowDto userShowDto = response.getBody();
        assertNotNull(userShowDto);
        assertEquals(user.getUsername(), userShowDto.getUsername());
        assertEquals(user.getEmail(), userShowDto.getEmail());
        assertEquals(user.getPhoneNumber(), userShowDto.getPhoneNumber());

        assertThrows(IncorrectDataException.class, () -> {
            adminService.getUserByUsername("nonexistentuser");
        });
    }

    @Test
    void testDeleteUserByUsername() throws Exception {

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
        usersRepository.save(user);

        when(usersRepository.findUserByUsername("testuser")).thenReturn(user);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        when(usersRepository.existsByUsername(user.getUsername())).thenReturn(true);

        ResponseEntity<String> response = adminService.deleteUserByUsername("testuser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String body = response.getBody();
        String expectedBody = "User testuser deleted";
        assertEquals(expectedBody, body);

        assertThrows(IncorrectDataException.class, () -> {
            adminService.getUserByUsername("nonexistentuser");
        });
    }

    @Test
    void testSetUserAdmin() throws Exception {

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
        usersRepository.save(user);

        when(usersRepository.findUserByUsername("testuser")).thenReturn(user);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        when(usersRepository.existsByUsername(user.getUsername())).thenReturn(true);

        ResponseEntity<UserShowDto> response = adminService.setUserAdmin("testuser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserShowDto userShowDto = response.getBody();
        assertNotNull(userShowDto);
        assertEquals(user.getUsername(), userShowDto.getUsername());
        assertEquals(user.getEmail(), userShowDto.getEmail());
        assertEquals(ERole.ADMIN, userShowDto.getRole());

        User savedUser = usersRepository.findUserByUsername("testuser");
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(ERole.ADMIN, savedUser.getRole());

        assertThrows(IncorrectDataException.class, () -> {
            adminService.getUserByUsername("nonexistentuser");
        });
    }

    @Test
    void testCreateUser() throws Exception {

        User existUser = new User();
        existUser.setId(1L);
        existUser.setUsername("testuser");
        existUser.setPassword("testpassword");
        existUser.setEmail("testuser@barters.web");
        existUser.setRole(ERole.USER);
        existUser.setDescription("testDescription");
        existUser.setPhoneNumber("+37000000000");
        existUser.setImage("testImage");
        existUser.setItems(new ArrayList<>());
        existUser.setTokens(new ArrayList<>());
        usersRepository.save(existUser);

        when(usersRepository.findUserByUsername("testuser")).thenReturn(existUser);
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        when(usersRepository.existsByUsername(existUser.getUsername())).thenReturn(true);
        when(usersRepository.existsByEmail(existUser.getEmail())).thenReturn(true);

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("newUsername");
        userCreateDto.setPassword("newPassword");
        userCreateDto.setEmail("newemail@barters.web");
        userCreateDto.setPhoneNumber("+37000000000");
        userCreateDto.setRole(ERole.USER);
        userCreateDto.setDescription("newDescription");
        userCreateDto.setImage("newImage");

        ResponseEntity<UserShowDto> response = adminService.createUser(userCreateDto);
        Mockito.verify(usersRepository, Mockito.times(2)).save(Mockito.any(User.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserShowDto userShowDto = response.getBody();
        assertNotNull(userShowDto);
        assertEquals(userCreateDto.getUsername(), userShowDto.getUsername());
        assertEquals(userCreateDto.getEmail(), userShowDto.getEmail());

        UserCreateDto userCreateDto1 = new UserCreateDto();
        userCreateDto1.setUsername("testuser");
        userCreateDto1.setPassword("newPassword");
        userCreateDto1.setEmail("testuser@barters.web");
        userCreateDto1.setPhoneNumber("+37000000000");
        userCreateDto1.setRole(ERole.USER);
        userCreateDto1.setDescription("newDescription");
        userCreateDto1.setImage("newImage");
        assertThrows(IncorrectDataException.class, () -> {
            adminService.createUser(userCreateDto1);
        });

        UserCreateDto userCreateDto2 = new UserCreateDto();
        userCreateDto2.setUsername("testuser");
        userCreateDto2.setPassword("newPassword");
        userCreateDto2.setEmail("uniqueemail@barters.web");
        userCreateDto2.setPhoneNumber("+37000000000");
        userCreateDto2.setRole(ERole.USER);
        userCreateDto2.setDescription("newDescription");
        userCreateDto2.setImage("newImage");
        assertThrows(IncorrectDataException.class, () -> {
            adminService.createUser(userCreateDto2);
        });

        UserCreateDto userCreateDto3 = new UserCreateDto();
        userCreateDto3.setUsername("uniqueUsername");
        userCreateDto3.setPassword("testpassword");
        userCreateDto3.setEmail("testuser@barters.web");
        userCreateDto3.setRole(ERole.USER);
        userCreateDto3.setDescription("testDescription");
        userCreateDto3.setPhoneNumber("+37000000000");
        userCreateDto3.setImage("testImage");
        assertThrows(IncorrectDataException.class, () -> {
            adminService.createUser(userCreateDto3);
        });

    }

}