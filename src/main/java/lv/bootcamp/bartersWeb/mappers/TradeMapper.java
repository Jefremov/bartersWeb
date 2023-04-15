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
        trade.setItem(mapToItem(tradeDto.getItemId()));
        trade.setOfferedItem(mapToItem(tradeDto.getOfferedItemId()));
        trade.setStatus(tradeDto.getStatus());
        trade.setComment(tradeDto.getComment());
        trade.setDate(tradeDto.getDate());
        return trade;
    }

    public TradeDto toDto(Trade trade) {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setId(trade.getId());
        tradeDto.setItemId(mapToItemId(trade.getItem()));
        tradeDto.setOfferedItemId(mapToItemId(trade.getOfferedItem()));
        tradeDto.setStatus(trade.getStatus());
        tradeDto.setComment(trade.getComment());
        tradeDto.setDate(trade.getDate());
        return tradeDto;
    }

    private Long mapToItemId(Item item) {
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
