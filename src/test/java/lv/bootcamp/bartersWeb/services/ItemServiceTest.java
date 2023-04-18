package lv.bootcamp.bartersWeb.services;

import com.amazonaws.services.s3.AmazonS3;
import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.mappers.ItemMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    private ItemRepository itemRepository;
    private TradeRepository tradeRepository;
    private UsersRepository usersRepository;
    private ItemMapper itemMapper;
    private AmazonS3 S3client;

    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        itemMapper = mock(ItemMapper.class);
        tradeRepository = mock(TradeRepository.class);
        itemRepository = mock(ItemRepository.class);
        usersRepository = mock(UsersRepository.class);
        S3client = mock(AmazonS3.class);
        itemService = new ItemService(itemRepository, tradeRepository, itemMapper, S3client, usersRepository);
    }

    @Test
    @DisplayName("Does not add Item Test")
    void testAddNoItem() throws IOException {

        ItemCreateDto itemCreateDto = Mockito.mock(ItemCreateDto.class);
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[0]);
        when(itemCreateDto.getFile()).thenReturn(file);

        Item item = Mockito.mock(Item.class);
        when(itemMapper.CreateDtoToItemFile(itemCreateDto, "src/main/resources/itemimages/test.jpg", null)).thenReturn(item);

        when(itemRepository.save(item)).thenReturn(item);

        ResponseEntity<String> responseEntity = itemService.addItem(itemCreateDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }
    @Test
    @DisplayName("Should return all available items in the database")
    public void shouldReturnAllAvailableItems() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setTitle("Item 1");
        item1.setStatus(EItemStatus.AVAILABLE);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setTitle("Item 2");
        item2.setStatus(EItemStatus.AVAILABLE);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        when(itemRepository.findAllByStatus(EItemStatus.AVAILABLE)).thenReturn(items);

        ItemDto itemDto1 = new ItemDto();
        itemDto1.setId(1L);
        itemDto1.setTitle("Item 1");

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setId(2L);
        itemDto2.setTitle("Item 2");

        List<ItemDto> expectedItemDtos = new ArrayList<>();
        expectedItemDtos.add(itemDto1);
        expectedItemDtos.add(itemDto2);

        when(itemMapper.itemToDto(ArgumentMatchers.any(Item.class))).thenReturn(itemDto1, itemDto2);

        List<ItemDto> actualItemDtos = itemService.getItems();

        assertEquals(expectedItemDtos, actualItemDtos);
    }
    @Test
    @DisplayName("Should return null when no items are available")
    public void shouldReturnNullWhenNoItemsAvailable() {
        List<Item> items = new ArrayList<>();
        when(itemRepository.findAllByStatus(EItemStatus.AVAILABLE)).thenReturn(items);

        List<ItemDto> actualItemDtos = itemService.getItems();

        assertNull(actualItemDtos);
    }
    @Test
    @DisplayName("Should return item with specified id")
    public void testGetItemByIdSuccessful() {
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        ItemDto itemDto = new ItemDto();
        itemDto.setId(itemId);

        when(itemRepository.existsById(itemId)).thenReturn(true);
        when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));
        when(itemMapper.itemToDto(item)).thenReturn(itemDto);

        ResponseEntity<ItemDto> responseEntity = itemService.getItemById(itemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(itemId, responseEntity.getBody().getId());
    }

    @Test
    @DisplayName("Should return not found for nonexistent item id")
    public void testGetItemByNonExistentId() {
        Long itemId = 1L;

        when(itemRepository.existsById(itemId)).thenReturn(false);
        ResponseEntity<ItemDto> responseEntity = itemService.getItemById(itemId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("Successfully updates item")
    void testUpdateItemSuccess() throws IOException {
        Long itemId = 1L;
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setTitle("Updated title");
        itemCreateDto.setState("Updated state");
        itemCreateDto.setCategory("ELECTRONICS");
        itemCreateDto.setDescription("Updated description");
        itemCreateDto.setStatus("AVAILABLE");
        itemCreateDto.setImageChange(false);
        Item item = new Item();
        item.setId(itemId);
        item.setTitle("Title");
        item.setState("State");
        item.setCategory(ECategory.ARTSUPPLIES);
        item.setDescription("Description");
        item.setStatus(EItemStatus.UNAVAILABLE);

        when(itemRepository.existsById(itemId)).thenReturn(true);
        when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));

        ResponseEntity response = itemService.updateItem(itemCreateDto, itemId);

        verify(itemRepository, times(1)).save(item);
        assertEquals(ResponseEntity.ok("Item updated"), response);
        assertEquals("Updated title", item.getTitle());
        assertEquals("Updated state", item.getState());
        assertEquals("ELECTRONICS", item.getCategory().name());
        assertEquals("Updated description", item.getDescription());
        assertEquals("AVAILABLE", item.getStatus().name());
    }

    @Test
    @DisplayName("Test updating a non-existent item")
    void testUpdateNonExistentItem() throws IOException {
        ItemCreateDto mockItemCreateDto = new ItemCreateDto();
        mockItemCreateDto.setTitle("Updated Test Item");
        mockItemCreateDto.setState("Used");
        mockItemCreateDto.setCategory("Updated Test Category");
        mockItemCreateDto.setDescription("Updated Test Description");

        when(itemRepository.existsById(2L)).thenReturn(false);

        ResponseEntity responseEntity = itemService.updateItem(mockItemCreateDto, 2L);

        verify(itemRepository, times(1)).existsById(2L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    @DisplayName("Test deleting an item with existing id")
    void testDeleteItemWithExistingId() {
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(itemRepository.existsById(itemId)).thenReturn(true);

        ResponseEntity responseEntity = itemService.deleteItemById(itemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Test deleteItem with non-existing id")
    void testDeleteItemWithNonExistingId() {
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        ResponseEntity responseEntity = itemService.deleteItemById(itemId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(itemRepository, never()).delete(any(Item.class));
    }

    @Test
    @DisplayName("Test get items by category")
    void testGetItemsByCategory() {
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setId(1L);
        item1.setTitle("Item 1");
        item1.setCategory(ECategory.CLOTHING);
        items.add(item1);

        when(itemRepository.findByCategory(ECategory.CLOTHING)).thenReturn(items);

        List<ItemDto> itemDtos = new ArrayList<>();
        ItemDto itemDto1 = new ItemDto();
        itemDto1.setId(1L);
        itemDto1.setTitle("Item 1");
        itemDto1.setCategory("CLOTHING");
        itemDtos.add(itemDto1);

        when(itemMapper.itemToDto(item1)).thenReturn(itemDto1);

        List<ItemDto> result = itemService.getItemsByCategory("CLOTHING");
        assertThat(result).isEqualTo(itemDtos);
    }

    @Test
    public void testSearchItemsByTitle_withMatchingTitle() {
        String title = "book";
        Item item1 = new Item();
        item1.setId(1L);
        item1.setTitle("Book of Knowledge");
        item1.setStatus(EItemStatus.AVAILABLE);
        Item item2 = new Item();
        item2.setId(2L);
        item2.setTitle("Harry Potter book");
        item2.setStatus(EItemStatus.AVAILABLE);
        List<Item> items = Arrays.asList(item1, item2);
        ItemDto itemDto1 = new ItemDto();
        itemDto1.setId(1L);
        itemDto1.setTitle("Book of Knowledge");
        ItemDto itemDto2 = new ItemDto();
        itemDto2.setId(2L);
        itemDto2.setTitle("Harry Potter book");
        List<ItemDto> expected = Arrays.asList(itemDto1, itemDto2);
        when(itemRepository.findByTitleContainingIgnoreCaseAndStatus(title, EItemStatus.AVAILABLE)).thenReturn(items);
        when(itemMapper.itemToDto(item1)).thenReturn(itemDto1);
        when(itemMapper.itemToDto(item2)).thenReturn(itemDto2);

        List<ItemDto> actual = itemService.searchItemsByTitle(title);

        assertEquals(expected, actual);
    }

    @Test
    public void testSearchItemsByTitle_withNonMatchingTitle() {
        String title = "car";
        when(itemRepository.findByTitleContainingIgnoreCaseAndStatus(title, EItemStatus.AVAILABLE)).thenReturn(Collections.emptyList());

        List<ItemDto> actual = itemService.searchItemsByTitle(title);

        assertNull(actual);
    }
}
