package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
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
        tradeDto.setId(1L);
        tradeDto.setItemId(3L);
        tradeDto.setOfferedItemId(4L);
        tradeDto.setStatus(EStatus.PENDING);
        tradeDto.setComment("Test comment");
        tradeDto.setDate(LocalDateTime.now());

        Trade trade = tradeMapper.toEntity(tradeDto);

        assertThat(trade.getId()).isEqualTo(tradeDto.getId());

        Item item = trade.getItem();
        assertThat(item).isNotNull();
        assertThat(item.getId()).isEqualTo(tradeDto.getItemId());

        Item offeredItem = trade.getOfferedItem();
        assertThat(offeredItem).isNotNull();
        assertThat(offeredItem.getId()).isEqualTo(tradeDto.getOfferedItemId());

        assertThat(trade.getStatus()).isEqualTo(tradeDto.getStatus());
        assertThat(trade.getComment()).isEqualTo(tradeDto.getComment());
        assertThat(trade.getDate()).isEqualTo(tradeDto.getDate());
    }


    @Test
    @DisplayName("Test mapping Trade entity to TradeDto")
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

        TradeDto tradeDto = tradeMapper.toDto(trade);

        assertEquals(trade.getId(), tradeDto.getId());
        assertEquals(trade.getItem().getId(), tradeDto.getItemId());
        assertEquals(trade.getOfferedItem().getId(), tradeDto.getOfferedItemId());
        assertEquals(trade.getStatus(), tradeDto.getStatus());
        assertEquals(trade.getComment(), tradeDto.getComment());
        assertEquals(trade.getDate(), tradeDto.getDate());
    }

    @Test
    @DisplayName("Test mapping null TradeDto to Trade entity")
    void testToEntityWithNullDto() {
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

        TradeDto tradeDto = tradeMapper.toDto(trade);
        Trade tradeFromDto = tradeMapper.toEntity(tradeDto);

        Assertions.assertEquals(tradeFromDto, trade);
    }
}

