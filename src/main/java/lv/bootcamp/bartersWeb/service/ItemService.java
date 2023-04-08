package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.ECategory;
import lv.bootcamp.bartersWeb.entity.EItemStatus;
import lv.bootcamp.bartersWeb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final Path root = Paths.get("src/main/resources/itemimages");

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> allItems(){
        return itemRepository.findAll();
    }
    public Item getItemsById(Long id){
        return itemRepository.findById(id).get();
    }
    public void addItem(String title, String state, ECategory category, String description, MultipartFile file, EItemStatus status, Long userId) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        String fileName = timestamp+file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
        Item item = new Item(title, state, description, root.toString()+"/"+fileName, category, status, userId);
        itemRepository.save(item);
    }

    public void deleteItemById(Long id){
        itemRepository.deleteById(id);
    }
}
