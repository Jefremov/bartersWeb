package lv.bootcamp.bartersWeb.repository;

import lv.bootcamp.bartersWeb.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends CrudRepository<Item, Long> {
}
