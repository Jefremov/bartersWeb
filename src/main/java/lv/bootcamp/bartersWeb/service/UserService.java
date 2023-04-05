package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entity.UserEntity;
import lv.bootcamp.bartersWeb.repository.UsersRepository;
import lv.bootcamp.bartersWeb.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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
        UserEntity user = userMapper.userRegisterDtoToUser(userRegisterDto);
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User " + userRegisterDto.getUsername() + " is registered");
    }
}
