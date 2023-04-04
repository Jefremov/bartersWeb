package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> allItems(){
        return itemRepository.findAll();

    }
}
