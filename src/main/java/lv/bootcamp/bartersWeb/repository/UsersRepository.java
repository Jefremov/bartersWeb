package lv.bootcamp.bartersWeb.repository;

import lv.bootcamp.bartersWeb.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
