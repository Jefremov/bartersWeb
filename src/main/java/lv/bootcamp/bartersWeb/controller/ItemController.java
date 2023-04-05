package lv.bootcamp.bartersWeb.controller;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.ItemCategory;
import lv.bootcamp.bartersWeb.service.ItemService;
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

    @GetMapping("/items")
    public List<Item> allItems(){
        return itemService.allItems();
    }
    @GetMapping("/item/{itemid}")
    public Item getItems(@PathVariable("itemid") Long itemid){
        return itemService.getItemsById(itemid);
    }
    @PostMapping("/addOrUpdateItem")
    public String addOrUpdateItem(@RequestParam("file")MultipartFile file, @RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("category") Integer c, @RequestParam("status") String status, @RequestParam("description") String description) throws IOException {
        ItemCategory category = ItemCategory.valueOf(c);
        itemService.addOrUpdateItem(id,title,status,category,description,file);
        return "New item added";
    }
    @DeleteMapping("/deleteItem/{itemid}")
    public String deleteItem(@PathVariable("itemid") Long itemid) {
        itemService.deleteItemById(itemid);
        return "Item deleted";
    }

}

