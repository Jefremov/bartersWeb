package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeMapper {

    @Autowired
    private ItemMapper itemMapper;

    public Trade toEntity(TradeDto tradeDto) {
        Trade trade = new Trade();
        trade.setId(tradeDto.getId());

        Item item = new Item();
        item.setId(tradeDto.getItemId());
        trade.setItem(item);

        Item offeredItem = new Item();
        offeredItem.setId(tradeDto.getOfferedItemId());
        trade.setOfferedItem(offeredItem);

        trade.setStatus(tradeDto.getStatus());
        trade.setComment(tradeDto.getComment());
        trade.setDate(tradeDto.getDate());
        return trade;
    }

    public TradeDto toDto(Trade trade) {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setId(trade.getId());
        if(trade.getItem() != null) {
            tradeDto.setItemId(trade.getItem().getId());
        }
        if(trade.getOfferedItem() != null) {
            tradeDto.setOfferedItemId(trade.getOfferedItem().getId());
        }
        tradeDto.setStatus(trade.getStatus());
        tradeDto.setComment(trade.getComment());
        tradeDto.setDate(trade.getDate());
        return tradeDto;
    }

}
