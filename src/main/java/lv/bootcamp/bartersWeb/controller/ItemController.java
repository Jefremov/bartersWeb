package lv.bootcamp.bartersWeb.controller;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.ECategory;
import lv.bootcamp.bartersWeb.entity.EItemStatus;
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
    @PostMapping("/addItem")
    public String addItem(@RequestParam("file")MultipartFile file, @RequestParam("title") String title, @RequestParam("category") Integer c, @RequestParam("state") String state, @RequestParam("description") String description, @RequestParam("status") String statusStr, @RequestParam("userId") Long userId) throws IOException {
        ECategory category = ECategory.valueOf(c);
        EItemStatus status = EItemStatus.valueOf(statusStr);
        itemService.addItem(title,state,category,description,file,status,userId);
        return "New item added";
    }
    @DeleteMapping("/deleteItem/{itemid}")
    public String deleteItem(@PathVariable("itemid") Long itemid) {
        itemService.deleteItemById(itemid);
        return "Item deleted";
    }

}

