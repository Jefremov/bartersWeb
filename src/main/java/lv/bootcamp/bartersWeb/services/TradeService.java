package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.TradeMapper;
import lv.bootcamp.bartersWeb.repositories.TradesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeService {

    private final TradesRepository tradesRepository;
    private final TradeMapper tradeMapper;

    @Autowired
    public TradeService(TradesRepository tradesRepository, TradeMapper tradeMapper) {
        this.tradesRepository = tradesRepository;
        this.tradeMapper = tradeMapper;
    }

    public List<TradeDto> getAllTrades() {
        List<Trade> trades = (List<Trade>) tradesRepository.findAll();
        if (trades.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trades found");
        }
        TradeMapper tradeMapper = new TradeMapper();
        return trades.stream().map(tradeMapper::toDto).collect(Collectors.toList());
    }

    public Trade createTrade(TradeDto tradeDto) {
        Trade trade = tradeMapper.toEntity(tradeDto);
        trade.setStatus(EStatus.PENDING);
        trade.setDate(new Date());

        return tradesRepository.save(trade);
    }

}