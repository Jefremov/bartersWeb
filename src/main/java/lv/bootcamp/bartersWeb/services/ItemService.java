package lv.bootcamp.bartersWeb.services;

import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.ItemMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final TradeRepository tradeRepository;
    private final ItemMapper itemMapper;
    private final Path root = Paths.get("src", "main", "resources", "static", "images");


    @Autowired
    public ItemService(ItemRepository itemRepository, TradeRepository tradeRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.tradeRepository = tradeRepository;
        this.itemMapper = itemMapper;
    }

    public ResponseEntity<String> addItem(ItemCreateDto itemCreateDto) throws IOException  {
        if (itemCreateDto.getFile().getSize()!=0) {
            MultipartFile file = itemCreateDto.getFile();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = now.format(formatter);
            String fileName = timestamp + file.getOriginalFilename();
            String filePath = "/images/" + fileName;
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            Item item = itemMapper.CreateDtoToItemFile(itemCreateDto, filePath);
            itemRepository.save(item);
            return ResponseEntity.ok("Added");
        }
        else return ResponseEntity.badRequest().build();
    }

    public List<ItemDto> getItems() {
        List<Item> items = itemRepository.findAllByStatus(EItemStatus.AVAILABLE);
        if(items.isEmpty()) return null;
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public ResponseEntity<ItemDto> getItemById(Long id) {
        if(itemRepository.existsById(id)) {
            Item item = itemRepository.findById(id).get();

            return ResponseEntity.ok().body(itemMapper.itemToDto(item));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity updateItem(ItemCreateDto itemCreateDto, Long id) throws IOException {
        if(itemRepository.existsById(id)) {
            Item item = itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));;
            if(itemCreateDto.isImageChange()) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                String timestamp = now.format(formatter);
                String fileName = timestamp + itemCreateDto.getFile().getOriginalFilename();
                Files.copy(itemCreateDto.getFile().getInputStream(), this.root.resolve(fileName));
                item.setImage(root.toString() + "/" + fileName);
            }
            item.setTitle(itemCreateDto.getTitle());
            item.setState(itemCreateDto.getState());
            item.setCategory(ECategory.valueOf(itemCreateDto.getCategory()));
            item.setDescription(itemCreateDto.getDescription());
            item.setStatus(EItemStatus.valueOf(itemCreateDto.getStatus()));
            itemRepository.save(item);
            return ResponseEntity.ok("Item updated");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteItemById(Long id) {
        if(itemRepository.existsById(id)){

            List<Trade> trades1 = tradeRepository.findByItemId(id);
            List<Trade> trades2 = tradeRepository.findByOfferedItemId(id);
            if(!trades1.isEmpty()){
                trades1.forEach(tradeRepository::delete);
            }
            if(!trades2.isEmpty()){
                trades2.forEach(tradeRepository::delete);
            }
            itemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
    }

    public List<ItemDto> getItemsByCategory(String category) {
        ECategory categoryEnum = ECategory.valueOf(category.toUpperCase());
        List<Item> items = itemRepository.findByCategory(categoryEnum);
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public List<ItemDto> searchItemsByTitle(String title) {
        List<Item> items = itemRepository.findByTitleContainingIgnoreCaseAndStatus(title, EItemStatus.AVAILABLE);
        if (items.isEmpty()) {
            return null;
        }
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

}
