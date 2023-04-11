package lv.bootcamp.bartersWeb.controller;

import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entity.ECategory;
import lv.bootcamp.bartersWeb.entity.EItemStatus;
import lv.bootcamp.bartersWeb.service.ItemService;
import lv.bootcamp.bartersWeb.service.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @Autowired
    private ItemMapper itemMapper;
    @PostMapping("/addItem")
    public String addItem(@RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("category") String categoryStr, @RequestParam("state") String state, @RequestParam("description") String description, @RequestParam("status") String statusStr, @RequestParam("userId") Long userId) throws IOException {
        ECategory category = ECategory.valueOf(categoryStr);
        EItemStatus status = EItemStatus.valueOf(statusStr);
        itemService.addItem(title,state,category,description,file,status,userId);
        return "New item added";
    }
    @GetMapping("/items")
    public List<ItemDto> getItems() {
        return itemService.getItems();
    }
    @GetMapping("/item/{itemid}")
    public ItemDto getItem(@PathVariable("itemid") Long itemid){
        return itemService.getItemById(itemid);
    }
    @PutMapping("/updateItem/{itemid}")
    public String updateItem(@PathVariable("itemid") Long itemid, @RequestParam("file") MultipartFile file, @RequestParam("title") String title, @RequestParam("category") String categoryStr, @RequestParam("state") String state, @RequestParam("description") String description, @RequestParam("status") String statusStr, @RequestParam("userId") Long userId) throws IOException {
        ECategory category = ECategory.valueOf(categoryStr);
        EItemStatus status = EItemStatus.valueOf(statusStr);
        itemService.updateItem(itemid,title,state,category,description,file,status,userId);
        return "Item updated";
    }
    @DeleteMapping("/deleteItem/{itemid}")
    public String deleteItem(@PathVariable("itemid") Long itemid) {
        itemService.deleteItemById(itemid);
        return "Item deleted";
    }

}

