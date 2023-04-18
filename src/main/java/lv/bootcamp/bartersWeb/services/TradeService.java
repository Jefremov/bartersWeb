package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TradeService {
    private static final Logger logger = LogManager.getLogger(TradeService.class);
    @Autowired
    private TradeRepository tradesRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TradeMapper tradeMapper;

    public List<TradeShowDto> getAllTrades() {
        try {
            return tradesRepository.findAll()
                    .stream()
                    .map(tradeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.warn("An error occurred while retrieving trades: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public ResponseEntity<Trade> createTrade(TradeDto tradeDto) {
        try {
            Trade trade = tradeMapper.toEntity(tradeDto);
            tradesRepository.save(trade);
            logger.info("Created trade successfully with ID: " + trade.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(trade);
        } catch (Exception e) {
            logger.error("An error occurred while creating trade", e);
            e.printStackTrace();
            throw new RuntimeException("Error occurred while creating trade");
        }
    }

    public void deleteTrade(Long id) {
        tradesRepository.deleteById(id);
        logger.info("Deleted trade with ID: " + id);
    }

    public String updateTradeStatus(Long id, EStatus status) {
        Trade trade = tradesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));
        if(status==EStatus.ACCEPTED){
            itemRepository.findById(trade.getItemId()).get().setStatus(EItemStatus.UNAVAILABLE);
            itemRepository.findById(trade.getOfferedItemId()).get().setStatus(EItemStatus.UNAVAILABLE);
        }
        trade.setStatus(status);
        tradesRepository.save(trade);
        logger.info("Updated trade status successfully for trade with ID: " + id);
        return "Trade status updated successfully";
    }

    public ResponseEntity<TradeShowDto> getTradeById(Long id) {
        TradeShowDto tradeShowDto = tradeMapper.toDto(tradesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found")));
        logger.info("Retrieved trade with ID: " + id);
        return ResponseEntity.ok().body(tradeShowDto);
    }
}
