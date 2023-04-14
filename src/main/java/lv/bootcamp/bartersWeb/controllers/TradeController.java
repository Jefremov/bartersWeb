package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.services.TradeService;
import lv.bootcamp.bartersWeb.utils.UpdateTradeStatusRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;
    private final TradeMapper tradeMapper;

    public TradeController(TradeService tradeService, TradeMapper tradeMapper) {
        this.tradeService = tradeService;
        this.tradeMapper = tradeMapper;
    }

    @GetMapping
    public List<TradeDto> allTrades() {
        return tradeService.getAllTrades();
    }

    @PostMapping("/create")
    public ResponseEntity<TradeDto> createTrade(@RequestBody TradeDto tradeDto) {
        Trade trade = tradeMapper.toEntity(tradeDto);
        Trade createdTrade = tradeService.createTrade(trade);
        TradeDto createdTradeDto = tradeMapper.toDto(createdTrade);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTradeDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
    }

    @PutMapping("/update/{id}")
    public String updateTradeStatus(@PathVariable Long id, @RequestBody UpdateTradeStatusRequest request) {
        return tradeService.updateTradeStatus(id, request.getStatus());
    }

}
