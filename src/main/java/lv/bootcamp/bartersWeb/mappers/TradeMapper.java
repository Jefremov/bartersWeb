package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TradeMapper {

    public Trade toEntity(TradeDto tradeDto) {
        Trade trade = new Trade();
        trade.setItemId(tradeDto.getItemId());
        trade.setOfferedItemId(tradeDto.getOfferedItemId());
        trade.setStatus(EStatus.PENDING);
        trade.setComment(tradeDto.getComment());
        trade.setDate(LocalDateTime.now());
        return trade;
    }

    public TradeShowDto toDto(Trade trade) {
        TradeShowDto tradeShowDto = new TradeShowDto();
        tradeShowDto.setId(trade.getId());
        tradeShowDto.setItemId(trade.getItemId());
        tradeShowDto.setOfferedItemId(trade.getOfferedItemId());
        tradeShowDto.setStatus(trade.getStatus());
        tradeShowDto.setComment(trade.getComment());
        tradeShowDto.setDate(trade.getDate());
        return tradeShowDto;
    }

    public Long mapToItemId(Item item) {
        return item != null ? item.getId() : null;
    }

    private Item mapToItem(Long itemId) {
        if (itemId == null) {
            return null;
        }
        Item item = new Item();
        item.setId(itemId);
        return item;
    }

}
