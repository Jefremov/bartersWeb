package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.stereotype.Component;

@Component
public class TradeMapper {

    public Trade toEntity(TradeDto tradeDto) {
        Trade trade = new Trade();
        trade.setId(tradeDto.getId());

        Item item = new Item();
        item.setId(Long.parseLong(tradeDto.getItemId()));
        trade.setItem(item);

        Item offeredItem = new Item();
        offeredItem.setId(Long.parseLong(tradeDto.getOfferedItemId()));
        trade.setOfferedItem(offeredItem);

        trade.setStatus(tradeDto.getStatus());
        trade.setComment(tradeDto.getComment());
        trade.setDate(tradeDto.getDate());
        return trade;
    }

    public TradeDto toDto(Trade trade) {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setId(trade.getId());
        tradeDto.setItemId(String.valueOf(trade.getItem().getId()));
        tradeDto.setOfferedItemId(String.valueOf(trade.getOfferedItem().getId()));
        tradeDto.setStatus(trade.getStatus());
        tradeDto.setComment(trade.getComment());
        tradeDto.setDate(trade.getDate());
        return tradeDto;
    }

}
