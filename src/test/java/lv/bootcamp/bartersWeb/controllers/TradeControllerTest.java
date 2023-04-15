package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.services.TradeService;
import lv.bootcamp.bartersWeb.utils.UpdateTradeStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

    private TradeController tradeController;

    @Mock
    private TradeService tradeService;

    @Mock
    private TradeMapper tradeMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tradeController = new TradeController(tradeService, tradeMapper);
    }

    @Test
    @DisplayName("Test get all trades controller")
    public void testAllTrades() {
        TradeShowDto tradeShowDto = new TradeShowDto();
        tradeShowDto.setId(1L);

        when(tradeService.getAllTrades()).thenReturn(List.of(tradeShowDto));

        List<TradeShowDto> tradeShowDtos = tradeController.allTrades();

        assertEquals(1, tradeShowDtos.size());
        assertThat(tradeShowDtos.get(0)).isEqualTo(tradeShowDto);
    }

    @Test
    @DisplayName("Test create trade controller")
    void testCreateTrade() {
        Trade trade = new Trade();
        TradeDto tradeDto= new TradeDto();
        // set up trade properties
        when(tradeService.createTrade(tradeDto)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(trade));

        ResponseEntity<Trade> responseEntity = tradeController.createTrade(tradeDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(trade);
    }

    @Test
    @DisplayName("Test delete trade controller")
    public void testDeleteTrade() {
        tradeController.deleteTrade(1L);
        verify(tradeService).deleteTrade(1L);
    }

    @Test
    @DisplayName("Test update trade status controller")
    public void testUpdateTradeStatus() {
        EStatus status = EStatus.PENDING;
        UpdateTradeStatusRequest request = new UpdateTradeStatusRequest();
        request.setStatus(status);

        when(tradeService.updateTradeStatus(1L, status)).thenReturn(String.valueOf(status));

        String response = tradeController.updateTradeStatus(1L, request);

        assertEquals(status.toString(), response);
    }
}
