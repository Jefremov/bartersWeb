package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
