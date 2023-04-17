package lv.bootcamp.bartersWeb.controllers;

import jakarta.validation.Valid;
import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@ModelAttribute @Valid ItemCreateDto itemCreateDto) throws IOException {
        return itemService.addItem(itemCreateDto);
    }
    @GetMapping
    public List<ItemDto> getItems() {
        return itemService.getItems();
    }
    @GetMapping("/{itemid}")
    public ResponseEntity<ItemDto> getItem(@PathVariable("itemid") Long itemid){
        return itemService.getItemById(itemid);
    }
    @PutMapping("/update/{itemid}")
    public ResponseEntity updateItem(@PathVariable("itemid") Long id, @ModelAttribute @Valid ItemCreateDto itemCreateDto) throws IOException {
        return itemService.updateItem(itemCreateDto, id);

    }
    @DeleteMapping("/delete/{itemid}")
    public ResponseEntity deleteItem(@PathVariable("itemid") Long itemId) {
        return itemService.deleteItemById(itemId);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ItemDto>> getItemsByCategory(@PathVariable String category) {
        List<ItemDto> items = itemService.getItemsByCategory(category);
        if(items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ItemDto>> getItemsByUser(@PathVariable String username) {
        List<ItemDto> items = itemService.getItemsByUser(username);
        if(items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String title) {
        List<ItemDto> items = itemService.searchItemsByTitle(title);
        if (items == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

}

