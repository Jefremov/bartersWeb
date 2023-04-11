package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradesRepository extends CrudRepository<Trade, Long> {
}
