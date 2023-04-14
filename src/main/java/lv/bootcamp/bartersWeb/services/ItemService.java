package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.mappers.ItemMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

    public ResponseEntity<List<String>> addItem(ItemCreateDto itemCreateDto) throws IOException  {
        if (itemCreateDto.getFile().getSize()!=0) {
            MultipartFile file = itemCreateDto.getFile();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = now.format(formatter);
            String fileName = timestamp + file.getOriginalFilename();
            String filePath = root.toString() + "/" + fileName;
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            Item item = itemMapper.CreateDtoToItemFile(itemCreateDto, filePath);
            itemRepository.save(item);
            return ResponseEntity.ok(Collections.singletonList("Added"));
        }
        else return ResponseEntity.ok().build();
    }

    public List<ItemDto> getItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public ItemDto getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        return itemMapper.itemToDto(item);
    }

    public void updateItem(Long id, String title, String state, ECategory category, String description, MultipartFile file, EItemStatus status, Long userId) throws IOException {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        String fileName = timestamp + file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.root.resolve(fileName));
        item.setTitle(title);
        item.setState(state);
        item.setCategory(category);
        item.setDescription(description);
        item.setImage(root.toString() + "/" + fileName);
        item.setStatus(status);
        item.setUserId(userId);
        itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
