package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserCreateDto;
import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.mappers.UserMapper;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private static Logger log = Logger.getLogger(AdminServiceImpl.class);
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseEntity<UserShowDto> getUserByUsername(String username) throws IncorrectDataException {

        userExistenceCheck(username);
        User user = usersRepository.findUserByUsername(username);
        UserShowDto showDto = userMapper.userToUserShowDto(user);
        return ResponseEntity.ok().body(showDto);
    }

    @Override
    public ResponseEntity<String> deleteUserByUsername(String username)
            throws IncorrectDataException {
        userExistenceCheck(username);
        usersRepository.deleteByUsername(username);
        log.info("User " + username + " deleted");
        return ResponseEntity.ok().body("User " + username + " deleted");
    }

    @Override
    public ResponseEntity<UserShowDto> setUserAdmin(String username) throws IncorrectDataException {
        userExistenceCheck(username);
        User user = usersRepository.findUserByUsername(username);
        user.setRole(ERole.ADMIN);
        usersRepository.save(user);
        UserShowDto userShowDto = userMapper.userToUserShowDto(user);
        log.info("Users " + username + " role is ADMIN");
        return ResponseEntity.ok().body(userShowDto);
    }

    @Override
    public ResponseEntity<UserShowDto> createUser(UserCreateDto userCreateDto) throws IncorrectDataException {


        if (usersRepository.existsByUsername(userCreateDto.getUsername()) &&
                usersRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new IncorrectDataException("Registration is not possible. Username " + userCreateDto.getUsername() +
                    " and email " + userCreateDto.getEmail() + " does exist");
        }
        if (usersRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new IncorrectDataException("Registration is not possible. Username " + userCreateDto.getUsername()
                    + " does exist");
        }
        if (usersRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new IncorrectDataException("Registration is not possible. Username " + userCreateDto.getEmail()
                    + " does exist");
        }
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        usersRepository.save(user);
        log.info("User " + user.getUsername() + " created");
        UserShowDto userShowDto = userMapper.userToUserShowDto(user);
        return ResponseEntity.ok().body(userShowDto);
    }


    private void userExistenceCheck(String username) throws IncorrectDataException {
        if (!usersRepository.existsByUsername(username)) {
            throw new IncorrectDataException("Username " + username + " does not exist");
        }
    }

}
