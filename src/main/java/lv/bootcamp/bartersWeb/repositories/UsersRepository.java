package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
