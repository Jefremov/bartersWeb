package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.dto.TradeShowOneDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.hibernate.type.descriptor.java.LocalDateJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TradeMapper {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UsersRepository usersRepository;

    public Trade toEntity(TradeDto tradeDto) {
        Trade trade = new Trade();
        trade.setItemId(tradeDto.getItemId());
        trade.setOfferedItemId(tradeDto.getOfferedItemId());
        trade.setStatus(EStatus.PENDING);
        trade.setComment(tradeDto.getComment());
        trade.setDate(LocalDateTime.now());
        return trade;
    }

    public TradeShowOneDto toOneDto(Trade trade) {
        TradeShowOneDto tradeShowDto = new TradeShowOneDto();
        tradeShowDto.setId(trade.getId());
        Item item= itemRepository.findById(trade.getItemId()).get();
        tradeShowDto.setItemId(trade.getItemId());
        tradeShowDto.setItemImage(item.getImage());
        tradeShowDto.setItemTitle(item.getTitle());
        tradeShowDto.setItemState(item.getState());
        tradeShowDto.setItemCategory(item.getCategory().getDisplayName());
        tradeShowDto.setItemDescription(item.getDescription());
        tradeShowDto.setItemUsername(usersRepository.findById(item.getUserId()).get().getUsername());
        tradeShowDto.setOfferedItemId(trade.getOfferedItemId());
        Item offeredItem = itemRepository.findById(trade.getOfferedItemId()).get();
        tradeShowDto.setOfferedItemImage(offeredItem.getImage());
        tradeShowDto.setOfferedItemTitle(offeredItem.getTitle());
        tradeShowDto.setOfferedItemState(offeredItem.getState());
        tradeShowDto.setOfferedItemCategory(offeredItem.getCategory().getDisplayName());
        tradeShowDto.setOfferedItemDescription(offeredItem.getDescription());
        tradeShowDto.setOfferedItemUsername(usersRepository.findById(offeredItem.getUserId()).get().getUsername());
        tradeShowDto.setStatus(trade.getStatus());
        tradeShowDto.setComment(trade.getComment());
        tradeShowDto.setDate(trade.getDate());
        return tradeShowDto;
    }
    public TradeShowDto toDto(Trade trade) {
        TradeShowDto tradeShowDto = new TradeShowDto();
        tradeShowDto.setId(trade.getId());
        Item item= itemRepository.findById(trade.getItemId()).get();
        tradeShowDto.setItemId(trade.getItemId());
        tradeShowDto.setItem(item.getTitle());
        tradeShowDto.setOfferedItemId(trade.getOfferedItemId());
        Item offeredItem = itemRepository.findById(trade.getOfferedItemId()).get();
        tradeShowDto.setOfferedItem(offeredItem.getTitle());
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
