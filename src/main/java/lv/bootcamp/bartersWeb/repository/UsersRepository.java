package lv.bootcamp.bartersWeb.repository;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.Review;
import lv.bootcamp.bartersWeb.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
