package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;

import lv.bootcamp.bartersWeb.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByCategory(ECategory category);
    List<Item> findByUserId(Long userId);
    List<Item> findAllByStatus(EItemStatus status);
    List<Item> findByTitleContainingIgnoreCaseAndStatus(String title, EItemStatus available);
    List<Item> findByUserIdNot(Long userId);
}
