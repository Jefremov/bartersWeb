package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
