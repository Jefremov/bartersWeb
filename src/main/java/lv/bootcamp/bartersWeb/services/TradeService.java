package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TradeService {

    @Autowired
    private TradeRepository tradesRepository;
    @Autowired
    private TradeMapper tradeMapper;

    private static final Logger logger = LogManager.getLogger(TradeService.class);

    public List<TradeDto> getAllTrades() {
        try {
            return tradesRepository.findAll()
                    .stream()
                    .map(tradeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.warn("An error occurred while retrieving trades: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Trade createTrade(Trade trade) {
        try {
            return tradesRepository.save(trade);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while creating trade");
        }
    }

    public void deleteTrade(Long id) {
        tradesRepository.deleteById(id);
    }

    public String updateTradeStatus(Long id, EStatus status) {
        Trade trade = tradesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));
        trade.setStatus(status);
        tradesRepository.save(trade);
        return "Trade status updated successfully";
    }

}
