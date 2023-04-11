package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import lv.bootcamp.bartersWeb.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UsersRepository usersRepository;
    UserMapper userMapper;

    @Autowired
    public UserService(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity registerUser(UserRegisterDto userRegisterDto) {

        if(usersRepository.existsByUsername(userRegisterDto.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User " + userRegisterDto.getUsername() + " is exist. Choose another user name");
        if(usersRepository.existsByEmail(userRegisterDto.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email " + userRegisterDto.getEmail() + " is exist.");
        User user = userMapper.userRegisterDtoToUser(userRegisterDto);
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User " + userRegisterDto.getUsername() + " is registered");
    }
}
