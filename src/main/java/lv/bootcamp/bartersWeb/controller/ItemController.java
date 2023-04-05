package lv.bootcamp.bartersWeb.controller;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String addOrUpdateItem(@RequestBody Item item) {
        itemService.addOrUpdateItem(item);
        return "New item added: " + item;
    }
    @DeleteMapping("/deleteItem/{itemid}")
    public String deleteItem(@PathVariable("itemid") Long itemid) {
        itemService.deleteItemById(itemid);
        return "Item deleted";
    }

}

