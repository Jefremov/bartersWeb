package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.User;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ItemMapperTest {
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private ItemMapper itemMapper;

    @Test
    @DisplayName("Should map Item to ItemDto")
    public void shouldMapItemToItemDto() {
        Item item = mock(Item.class);
        when(item.getId()).thenReturn(1L);
        when(item.getTitle()).thenReturn("Title");
        when(item.getImage()).thenReturn("Image");
        when(item.getDescription()).thenReturn("Description");
        when(item.getCategory()).thenReturn(ECategory.ELECTRONICS);
        when(item.getState()).thenReturn("New");
        when(item.getDate()).thenReturn(LocalDateTime.now());
        when(item.getStatus()).thenReturn(EItemStatus.AVAILABLE);
        when(item.getUserId()).thenReturn(1L);

        ItemDto itemDto = itemMapper.itemToDto(item);

        assertEquals(1L, itemDto.getId());
        assertEquals("Title", itemDto.getTitle());
        assertEquals("Image", itemDto.getImage());
        assertEquals("Description", itemDto.getDescription());
        assertEquals("Electronics", itemDto.getCategory());
        assertEquals("New", itemDto.getState());
        assertEquals(EItemStatus.AVAILABLE.getDisplayName(), itemDto.getStatus());
        assertEquals(1L, itemDto.getUserId());
    }

    @Test
    @DisplayName("Should map ItemCreateDto to Item")
    public void shouldMapItemCreateDtoToItem() {
        User user = new User();
        user.setId(1L);
        String username = "testuser";
        user.setUsername(username);
        when(usersRepository.findUserByUsername(username)).thenReturn(user);

        ItemCreateDto itemCreateDto = mock(ItemCreateDto.class);
        when(itemCreateDto.getTitle()).thenReturn("Title");
        when(itemCreateDto.getDescription()).thenReturn("Description");
        when(itemCreateDto.getCategory()).thenReturn("ELECTRONICS");
        when(itemCreateDto.getState()).thenReturn("New");

        Item item = itemMapper.CreateDtoToItemFile(itemCreateDto, null, username);

        assertEquals("Title", item.getTitle());
        assertEquals("Description", item.getDescription());
        assertEquals(ECategory.ELECTRONICS, item.getCategory());
        assertEquals("New", item.getState());
        assertEquals(EItemStatus.AVAILABLE, item.getStatus());
        assertEquals(1L, item.getUserId());
    }
}
