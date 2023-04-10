package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends CrudRepository<Item, Long> {
}
