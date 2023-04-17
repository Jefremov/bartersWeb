package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
