package lv.bootcamp.bartersWeb.repositories;

import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

    @Mock
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Test find by category")
    void testFindByCategory() {
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setId(1L);
        item1.setTitle("Item 1");
        item1.setCategory(ECategory.CLOTHING);
        items.add(item1);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setTitle("Item 2");
        item2.setCategory(ECategory.ELECTRONICS);
        items.add(item2);

        when(itemRepository.findByCategory(ECategory.CLOTHING)).thenReturn(items);

        List<Item> foundItems = itemRepository.findByCategory(ECategory.CLOTHING);
        assertThat(foundItems).isNotEmpty().containsExactlyInAnyOrder(item1, item2);
    }

    @Test
    @DisplayName("Test find all by status")
    public void testFindAllByStatus() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setTitle("Item 1");
        item1.setStatus(EItemStatus.AVAILABLE);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setTitle("Item 2");
        item2.setStatus(EItemStatus.UNAVAILABLE);

        when(itemRepository.findAllByStatus(EItemStatus.AVAILABLE)).thenReturn(Arrays.asList(item1));

        List<Item> result = itemRepository.findAllByStatus(EItemStatus.AVAILABLE);

        assertEquals(1, result.size());
        assertEquals(item1.getId(), result.get(0).getId());
        assertEquals(item1.getTitle(), result.get(0).getTitle());
        assertEquals(item1.getStatus(), result.get(0).getStatus());
    }
}