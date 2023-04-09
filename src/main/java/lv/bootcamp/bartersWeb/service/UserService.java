package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entity.User;
import lv.bootcamp.bartersWeb.repository.UsersRepository;
import lv.bootcamp.bartersWeb.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


@Service
public class UserService {

    UsersRepository usersRepository;
    UserMapper userMapper;



    @Autowired
    public UserService(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity registerUser(UserRegisterDto userRegisterDto) throws MethodArgumentNotValidException, NoSuchMethodException {
        if (usersRepository.existsByUsername(userRegisterDto.getUsername())) {
            BindingResult bindingResult = new BeanPropertyBindingResult(userRegisterDto, "userRegisterDto");
            FieldError fieldError = new FieldError("userRegisterDto", "Username", "already exist. Choose another username");
            bindingResult.addError(fieldError);
            throw new MethodArgumentNotValidException(
                    new MethodParameter(getClass().getMethod("registerUser", UserRegisterDto.class), 0), bindingResult);
        }
         if (usersRepository.existsByEmail(userRegisterDto.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email " + userRegisterDto.getEmail() + " is registered already.");

        User user = userMapper.userRegisterDtoToUser(userRegisterDto);
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User " + userRegisterDto.getUsername() + " has been registered successfully.");
    }
}
