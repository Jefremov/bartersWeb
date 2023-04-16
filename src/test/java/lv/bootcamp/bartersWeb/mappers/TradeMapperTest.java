package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.TradeDto;
import lv.bootcamp.bartersWeb.dto.TradeShowOneDto;
import lv.bootcamp.bartersWeb.entities.*;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TradeMapperTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private TradeMapper tradeMapper;

    @Test
    @DisplayName("Test mapping TradeDto to Trade entity")
    public void testToEntity() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setItemId(1L);
        tradeDto.setOfferedItemId(2L);
        tradeDto.setComment("test comment");

        Trade trade = tradeMapper.toEntity(tradeDto);

        assertEquals(tradeDto.getItemId(), trade.getItemId());
        assertEquals(tradeDto.getOfferedItemId(), trade.getOfferedItemId());
        assertEquals(EStatus.PENDING, trade.getStatus());
        assertEquals(tradeDto.getComment(), trade.getComment());
        assertEquals(LocalDateTime.now().getDayOfYear(), trade.getDate().getDayOfYear());
    }

    @Test
    @DisplayName("Test mapping Trade entity to TradeShowDto")
    public void testToOneDto() {
        Item item = new Item();
        item.setId(1L);
        item.setTitle("test item");
        item.setState("new");
        item.setCategory(ECategory.ELECTRONICS);
        item.setDescription("test description");
        item.setUserId(1L);

        Item offeredItem = new Item();
        offeredItem.setId(2L);
        offeredItem.setTitle("test offered item");
        offeredItem.setState("used");
        offeredItem.setCategory(ECategory.CLOTHING);
        offeredItem.setDescription("test offered description");
        offeredItem.setUserId(2L);

        Trade trade = new Trade();
        trade.setId(1L);
        trade.setItemId(1L);
        trade.setOfferedItemId(2L);
        trade.setStatus(EStatus.ACCEPTED);
        trade.setComment("test comment");
        trade.setDate(LocalDateTime.of(2023, 4, 15, 12, 0, 0));

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(offeredItem));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(usersRepository.findById(2L)).thenReturn(Optional.of(new User()));

        TradeShowOneDto tradeShowOneDto = tradeMapper.toOneDto(trade);

        assertEquals(trade.getId(), tradeShowOneDto.getId());
        assertEquals(item.getId(), tradeShowOneDto.getItemId());
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

