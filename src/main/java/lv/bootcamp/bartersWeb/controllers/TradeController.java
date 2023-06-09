package lv.bootcamp.bartersWeb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.services.TradeService;
import lv.bootcamp.bartersWeb.utils.UpdateTradeStatusRequest;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {
    private static Logger logger = Logger.getLogger(TradeController.class);
    private final TradeService tradeService;
    private final TradeMapper tradeMapper;

    public TradeController(TradeService tradeService, TradeMapper tradeMapper) {
        this.tradeService = tradeService;
        this.tradeMapper = tradeMapper;
    }

    @GetMapping
    public List<TradeShowDto> allTrades() {
        logger.info("Fetching all trades");
        return tradeService.getAllTrades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeShowDto> oneTrade(@PathVariable Long id) {
        logger.info("Fetching trade with ID: " + id);
        return tradeService.getTradeById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Trade> createTrade(@ModelAttribute @Valid TradeDto tradeDto) {
        logger.info("Creating a new trade");
        return tradeService.createTrade(tradeDto);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(hidden = true)
    public void deleteTrade(@PathVariable Long id) {
        logger.info("Deleting trade with ID: " + id);
        tradeService.deleteTrade(id);
    }

    @PutMapping("/update/{id}")
    public String updateTradeStatus(@PathVariable Long id, @ModelAttribute UpdateTradeStatusRequest request) {
        logger.info("Updating trade status with ID: " + id);
        return tradeService.updateTradeStatus(id, request.getStatus());
    }

}
