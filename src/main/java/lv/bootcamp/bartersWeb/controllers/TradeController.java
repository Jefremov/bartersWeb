package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradesRepository;
import lv.bootcamp.bartersWeb.services.TradeService;
import lv.bootcamp.bartersWeb.utils.UpdateTradeStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private TradesRepository tradesRepository;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TradeMapper tradeMapper;

    @GetMapping
    public List<Trade> allTrades() {
        return (List<Trade>) tradesRepository.findAll();
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
    public TradeDto updateTradeStatus(@PathVariable Long id, @RequestBody UpdateTradeStatusRequest request) {
        return tradeService.updateTradeStatus(id, request.getStatus());
    }

}
