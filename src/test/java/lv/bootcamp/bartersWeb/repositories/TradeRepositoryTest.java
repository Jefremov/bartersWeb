package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeRepositoryTest {

    @Mock
    private TradeRepository tradeRepository;

    @Test
    @DisplayName("Test find by item id")
    public void testFindByItemId() {
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setItemId(1L);
        trade.setOfferedItemId(2L);
        trade.setStatus(EStatus.PENDING);
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);

        when(tradeRepository.findByItemId(1L)).thenReturn(trades);

        List<Trade> foundTrades = tradeRepository.findByItemId(1L);

        assertEquals(foundTrades.size(), 1);
        assertEquals(foundTrades.get(0).getId(), 1L);
        assertEquals(foundTrades.get(0).getItemId(), 1L);
        assertEquals(foundTrades.get(0).getOfferedItemId(), 2L);
        assertEquals(foundTrades.get(0).getStatus(), EStatus.PENDING);
    }

    @Test
    @DisplayName("Test find by offered item id")
    public void testFindByOfferedItemId() {
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setItemId(1L);
        trade.setOfferedItemId(2L);
        trade.setStatus(EStatus.PENDING);
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);

        when(tradeRepository.findByOfferedItemId(2L)).thenReturn(trades);

        List<Trade> foundTrades = tradeRepository.findByOfferedItemId(2L);

        assertEquals(foundTrades.size(), 1);
        assertEquals(foundTrades.get(0).getId(), 1L);
        assertEquals(foundTrades.get(0).getItemId(), 1L);
        assertEquals(foundTrades.get(0).getOfferedItemId(), 2L);
        assertEquals(foundTrades.get(0).getStatus(), EStatus.PENDING);
    }
}