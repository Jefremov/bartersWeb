package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.repositories.TradesRepository;
import lv.bootcamp.bartersWeb.services.TradeService;
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

    @GetMapping
    public ResponseEntity<?> getAllTrades() {
        List<TradeDto> tradeDtos = tradeService.getAllTrades();
        if (tradeDtos.isEmpty()) {
            return ResponseEntity.ok("There are no trades created yet");
        } else {
            return ResponseEntity.ok(tradeDtos);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Trade> createTrade(@RequestBody TradeDto tradeDto) {
        Trade createdTrade = tradeService.createTrade(tradeDto);
        return new ResponseEntity<>(createdTrade, HttpStatus.CREATED);
    }
}
