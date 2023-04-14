package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.controllers.TradeController;
import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TradeServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    TradeController tradeController;

    @Mock
    TradeService tradeService;

    @Mock
    TradeMapper tradeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create trade")
    void shouldCreateTrade() {
        TradeDto tradeDto = new TradeDto();
        Trade trade = new Trade();
        TradeDto createdTradeDto = new TradeDto();
        createdTradeDto.setId(1L);

        when(tradeMapper.toEntity(tradeDto)).thenReturn(trade);
        when(tradeMapper.toDto(trade)).thenReturn(createdTradeDto);

        ResponseEntity<TradeDto> responseEntity = tradeController.createTrade(tradeDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should delete trade")
    void shouldDeleteTrade() {
        Long tradeId = 1L;

        tradeController.deleteTrade(tradeId);

        Assertions.assertDoesNotThrow(() -> tradeService.deleteTrade(tradeId));
    }

}
