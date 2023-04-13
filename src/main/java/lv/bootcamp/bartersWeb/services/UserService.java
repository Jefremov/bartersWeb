package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserRegisterDto;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import lv.bootcamp.bartersWeb.mappers.UserMapper;
import org.apache.log4j.Logger;
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

    private static Logger log = Logger.getLogger(UserService.class);

    UsersRepository usersRepository;
    UserMapper userMapper;

    @Autowired
    public UserService(UsersRepository usersRepository, UserMapper userMapper) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
    }

//    public ResponseEntity registerUser(UserRegisterDto userRegisterDto) throws MethodArgumentNotValidException, NoSuchMethodException {
//        if (usersRepository.existsByUsername(userRegisterDto.getUsername())) {
//            log.info("Registering a user with an existing username " + userRegisterDto.getUsername());
//            BindingResult bindingResult = new BeanPropertyBindingResult(userRegisterDto, "userRegisterDto");
//            FieldError fieldError = new FieldError("userRegisterDto", "Username " + userRegisterDto.getUsername(), "already exist. Choose another username");
//            bindingResult.addError(fieldError);
//            throw new MethodArgumentNotValidException(
//                    new MethodParameter(getClass().getMethod("registerUser", UserRegisterDto.class), 0), bindingResult);
//        }
//        if (usersRepository.existsByEmail(userRegisterDto.getEmail())) {
//            log.info("Registering a user with an existing email " + userRegisterDto.getEmail());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email " + userRegisterDto.getEmail() + " is registered already.");
//        }
//        User user = userMapper.userRegisterDtoToUser(userRegisterDto);
//        usersRepository.save(user);
//        log.info("new user registered " + user.toString());
//        return ResponseEntity.status(HttpStatus.OK).body("User " + userRegisterDto.getUsername() + " has been registered successfully.");
//    }
}
