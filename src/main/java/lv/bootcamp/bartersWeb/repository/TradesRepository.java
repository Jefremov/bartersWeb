package lv.bootcamp.bartersWeb.repository;

import lv.bootcamp.bartersWeb.entity.Trade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradesRepository extends CrudRepository<Trade, Long> {
}
