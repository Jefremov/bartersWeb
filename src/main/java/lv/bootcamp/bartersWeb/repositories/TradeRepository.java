package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByItemId(Long itemId);

    List<Trade> findByOfferedItemId(Long offeredItemId);
}
