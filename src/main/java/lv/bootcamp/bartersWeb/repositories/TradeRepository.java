package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

}
