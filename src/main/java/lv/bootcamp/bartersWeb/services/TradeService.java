package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TradeService {

    private final TradesRepository tradesRepository;
    private final ItemRepository itemRepository;
    private final TradeMapper tradeMapper;

    public TradeService(TradesRepository tradesRepository, ItemRepository itemsRepository, TradeMapper tradeMapper) {
        this.tradesRepository = tradesRepository;
        this.itemRepository = itemsRepository;
        this.tradeMapper = tradeMapper;
    }

    public List<Trade> allTrades() {
        return (List<Trade>) tradesRepository.findAll();
    }


    public Trade createTrade(Trade trade) {
        return tradesRepository.save(trade);
    }


    public void deleteTrade(Long id) {
        tradesRepository.deleteById(id);
    }

    public TradeDto updateTradeStatus(Long id, EStatus status) {
        Trade trade = tradesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));
        trade.setStatus(status);
        tradesRepository.save(trade);
        return tradeMapper.toDto(trade);
    }

}
