package lv.bootcamp.bartersWeb.service;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entity.ECategory;
import lv.bootcamp.bartersWeb.entity.EItemStatus;
import lv.bootcamp.bartersWeb.service.mapper.ItemMapper;
import lv.bootcamp.bartersWeb.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final Path root = Paths.get("src/main/resources/itemimages");

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Autowired
    private ItemMapper itemMapper;

    public void addItem(String title, String state, ECategory category, String description, MultipartFile file, EItemStatus status, Long userId) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        String fileName = timestamp+file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
        Item item = new Item(title, state, description, root.toString()+"/"+fileName, category, status, userId);
        itemRepository.save(item);
    }
    public List<ItemDto> getItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
    public ItemDto getItemById(Long id){
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        return itemMapper.itemToDto(item);
    }
    public void updateItem(Long id, String title, String state, ECategory category, String description, MultipartFile file, EItemStatus status, Long userId) throws IOException {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        String fileName = timestamp+file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
        item.setTitle(title);
        item.setState(state);
        item.setCategory(category);
        item.setDescription(description);
        item.setImage(root.toString()+"/"+fileName);
        item.setStatus(status);
        item.setUserId(userId);
        itemRepository.save(item);
    }
    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
