package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeMapper tradeMapper;

    @Test
    @DisplayName("Test getAllTrades() method")
    public void testGetAllTrades() {
        List<Trade> trades = new ArrayList<>();
        trades.add(new Trade());
        trades.add(new Trade());
        when(tradeRepository.findAll()).thenReturn(trades);

        TradeDto tradeDto = new TradeDto();
        when(tradeMapper.toDto(any(Trade.class))).thenReturn(tradeDto);

        List<TradeDto> tradeDtos = tradeService.getAllTrades();

        assertNotNull(tradeDtos);
        assertEquals(trades.size(), tradeDtos.size());
        verify(tradeRepository, times(1)).findAll();
        verify(tradeMapper, times(trades.size())).toDto(any(Trade.class));
    }

    @Test
    @DisplayName("Should create a trade")
    void testCreateTrade() {
        Trade trade = new Trade();
        Mockito.when(tradeRepository.save(trade)).thenReturn(trade);
        Trade createdTrade = tradeService.createTrade(trade);
        Assertions.assertEquals(trade, createdTrade);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    @DisplayName("Should handle an exception when creating a trade")
    void testCreateTradeException() {
        Trade trade = new Trade();
        Mockito.when(tradeRepository.save(trade)).thenThrow(new RuntimeException("Database connection error"));
        Assertions.assertThrows(RuntimeException.class, () -> tradeService.createTrade(trade));
    }
    

    @Test
    @DisplayName("Test deleteTrade() method")
    public void testDeleteTrade() {
        Long id = 1L;

        tradeService.deleteTrade(id);

        verify(tradeRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Test updateTradeStatus() method - trade exists")
    public void testUpdateTradeStatus_TradeExists() {
        Long id = 1L;
        Trade trade = new Trade();
        trade.setStatus(EStatus.PENDING);
        Optional<Trade> optionalTrade = Optional.of(trade);
        when(tradeRepository.findById(id)).thenReturn(optionalTrade);

        String result = tradeService.updateTradeStatus(id, EStatus.DECLINED);

        assertEquals("Trade status updated successfully", result);
        assertEquals(EStatus.DECLINED, trade.getStatus());
        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    @DisplayName("Test updateTradeStatus() method - trade does not exist")
    public void testUpdateTradeStatus_TradeDoesNotExist() {
        Long id = 1L;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> tradeService.updateTradeStatus(id, EStatus.ACCEPTED));
        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, never()).save(any(Trade.class));
    }

}

