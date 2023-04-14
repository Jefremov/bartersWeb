package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowDto;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("TradeMapper tests")
class TradeMapperTest {

    private final TradeMapper tradeMapper = new TradeMapper();

    @Test
    @DisplayName("Test mapping TradeDto to Trade entity")
    void testToEntity() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setItemId(3L);
        tradeDto.setOfferedItemId(4L);
        tradeDto.setComment("Test comment");

        Trade trade = tradeMapper.toEntity(tradeDto);

        Item item = trade.getItem();
        assertThat(item).isNotNull();
        assertThat(item.getId()).isEqualTo(tradeDto.getItemId());

        Item offeredItem = trade.getOfferedItem();
        assertThat(offeredItem).isNotNull();
        assertThat(offeredItem.getId()).isEqualTo(tradeDto.getOfferedItemId());

        assertThat(trade.getComment()).isEqualTo(tradeDto.getComment());
    }

    @Test
    @DisplayName("Test mapping Trade entity to TradeShowDto")
    void testToDto() {
        Item item = new Item();
        item.setId(2L);
        Item offeredItem = new Item();
        offeredItem.setId(3L);

        Trade trade = new Trade();
        trade.setId(1L);
        trade.setItem(item);
        trade.setOfferedItem(offeredItem);
        trade.setStatus(EStatus.PENDING);
        trade.setComment("Test comment");
        trade.setDate(LocalDateTime.now());

        TradeShowDto tradeShowDto = tradeMapper.toDto(trade);

        assertEquals(trade.getId(), tradeShowDto.getId());
        assertEquals(trade.getItem().getId(), tradeShowDto.getItemId());
        assertEquals(trade.getOfferedItem().getId(), tradeShowDto.getOfferedItemId());
        assertEquals(trade.getStatus(), tradeShowDto.getStatus());
        assertEquals(trade.getComment(), tradeShowDto.getComment());
        assertEquals(trade.getDate(), tradeShowDto.getDate());
    }

    @Test
    @DisplayName("Mapping Item to item ID")
    public void testMapToItemId() {
        Item item = new Item();
        item.setId(1L);

        Long itemId = tradeMapper.mapToItemId(item);

        assertEquals(item.getId(), itemId);
    }
}

