package lv.bootcamp.bartersWeb.repository;

import lv.bootcamp.bartersWeb.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
}
