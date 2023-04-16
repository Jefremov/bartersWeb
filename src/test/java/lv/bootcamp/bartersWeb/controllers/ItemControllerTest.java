package lv.bootcamp.bartersWeb.controllers;

import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    private  ItemService itemService;
    private  ItemController itemController;

    @BeforeEach
    public void setUp() {
        itemService = mock(ItemService.class);
        itemController = new ItemController(itemService);
    }

    @Test
    @DisplayName("Add item with file")
    void addItemWithFile() throws IOException {
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setTitle("Test item");
        itemCreateDto.setDescription("Test item description");
        itemCreateDto.setCategory("ELECTRONICS");
        itemCreateDto.setState("NEW");
        itemCreateDto.setStatus("AVAILABLE");
        MockMultipartFile file = new MockMultipartFile("file", "test-image.png",
                "image/png", "test-image".getBytes());
        itemCreateDto.setFile(file);

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Added");

        when(itemService.addItem(any(ItemCreateDto.class))).thenReturn(expectedResponse);
        ResponseEntity<String> actualResponse = itemController.addItem(itemCreateDto);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Add item with empty file")
    void addItemWithEmptyFile() throws IOException {
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setTitle("Test item");
        itemCreateDto.setDescription("Test item description");
        itemCreateDto.setCategory("ELECTRONICS");
        itemCreateDto.setState("used");
        itemCreateDto.setStatus("AVAILABLE");
        MockMultipartFile file = new MockMultipartFile("file", new byte[0]);
        itemCreateDto.setFile(file);

        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().build();

        when(itemService.addItem(any(ItemCreateDto.class))).thenReturn(expectedResponse);
        ResponseEntity<String> actualResponse = itemController.addItem(itemCreateDto);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    @DisplayName("Getting all items returns items")
    public void allItemsReturnsItems() {
        // Given
        ItemDto item1 = new ItemDto();
        item1.setId(1L);
        item1.setTitle("Item 1");

        ItemDto item2 = new ItemDto();
        item2.setId(2L);
        item2.setTitle("Item 2");

        List<ItemDto> items = Arrays.asList(item1, item2);

        when(itemService.getItems()).thenReturn(items);

        // When
        List<ItemDto> response = itemController.getItems();

        assert(response.size() == 2);
        assert(response.get(0).getTitle().equals("Item 1"));
        assert(response.get(1).getTitle().equals("Item 2"));
    }

    @Test
    @DisplayName("Getting all items returns empty list")
    public void allItemsReturnsEmptyList() {
        when(itemService.getItems()).thenReturn(Arrays.asList());

        List<ItemDto> response = itemController.getItems();

        assert(response.size() == 0);
    }
    @Test
    @DisplayName("Test getItem success")
    public void testGetItemSuccess() {
        long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        ItemDto expectedItemDto = new ItemDto();
        expectedItemDto.setId(itemId);
        ResponseEntity<ItemDto> expectedResponse = ResponseEntity.ok().body(expectedItemDto);

        when(itemService.getItemById(itemId)).thenReturn(expectedResponse);

        ResponseEntity<ItemDto> actualResponse = itemController.getItem(itemId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test getItem not found")
    public void testGetItemNotFound() {
        long itemId = 1L;
        ResponseEntity<ItemDto> expectedResponse = ResponseEntity.notFound().build();

        when(itemService.getItemById(itemId)).thenReturn(expectedResponse);

        ItemController itemController = new ItemController(itemService);

        ResponseEntity<ItemDto> actualResponse = itemController.getItem(itemId);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    @DisplayName("Test update item")
    void testUpdateItem() throws IOException {

        long itemId = 1L;
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        ResponseEntity expectedResponse = new ResponseEntity<>("Item updated", HttpStatus.OK);

        when(itemService.updateItem(itemCreateDto, itemId)).thenReturn(expectedResponse);

        ResponseEntity response = itemController.updateItem(itemId, itemCreateDto);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Test update item not found")
    void testUpdateItemNotFound() throws IOException {

        long itemId = 1L;
        ItemCreateDto itemCreateDto = new ItemCreateDto();
        ResponseEntity expectedResponse = ResponseEntity.notFound().build();

        when(itemService.updateItem(itemCreateDto, itemId)).thenReturn(expectedResponse);

        ResponseEntity response = itemController.updateItem(itemId, itemCreateDto);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
    @Test
    @DisplayName("Delete item by id success")
    void testDeleteItemByIdSuccess() {
        when(itemService.deleteItemById(anyLong()))
                .thenReturn(ResponseEntity.ok("Item deleted"));
        ResponseEntity response = itemController.deleteItem(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete item by id not found")
    void testDeleteItemByIdNotFound() {
        when(itemService.deleteItemById(anyLong()))
                .thenReturn(ResponseEntity.notFound().build());
        ResponseEntity response = itemController.deleteItem(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test get items by category")
    void testGetItemsByCategory() {
        List<ItemDto> items = new ArrayList<>();
        ItemDto item1 = new ItemDto();
        item1.setId(1L);
        item1.setTitle("Item 1");
        item1.setCategory("CLOTHING");
        items.add(item1);

        when(itemService.getItemsByCategory("CLOTHING")).thenReturn(items);

        ResponseEntity<List<ItemDto>> response = itemController.getItemsByCategory("CLOTHING");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull().isEqualTo(items);
    }

    @Test
    @DisplayName("Test get items by non-existing category")
    void testGetItemsByNonExistingCategory() {
        when(itemService.getItemsByCategory("FOOD")).thenReturn(new ArrayList<>());

        ResponseEntity<List<ItemDto>> response = itemController.getItemsByCategory("FOOD");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}
