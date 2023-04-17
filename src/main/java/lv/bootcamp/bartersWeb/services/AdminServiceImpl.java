package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.UserShowDto;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.exceptions.IncorrectDataException;
import lv.bootcamp.bartersWeb.mappers.UserMapper;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseEntity<UserShowDto> getUserByUsername(String username) throws IncorrectDataException {

        if (!usersRepository.existsByUsername(username)) {
            throw new IncorrectDataException("Username " + username+ " does not exist");
        }
        User user = usersRepository.findUserByUsername(username);
        UserShowDto showDto = userMapper.userToUserShowDto(user);
        return ResponseEntity.ok().body(showDto);
    }
}
