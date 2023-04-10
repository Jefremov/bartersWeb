package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradesRepository extends CrudRepository<Trade, Long> {

    @Query("SELECT t FROM Trade t JOIN FETCH t.item JOIN FETCH t.offeredItem")
    List<Trade> findAllWithItems();
}
