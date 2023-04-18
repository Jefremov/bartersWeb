package lv.bootcamp.bartersWeb.services;

import com.amazonaws.services.s3.AmazonS3;
import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.entities.Trade;
import lv.bootcamp.bartersWeb.mappers.ItemMapper;
import lv.bootcamp.bartersWeb.repositories.ItemRepository;
import lv.bootcamp.bartersWeb.repositories.TradeRepository;
import org.apache.log4j.Logger;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private static Logger logger = Logger.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final TradeRepository tradeRepository;
    private final UsersRepository userRepository;
    private final ItemMapper itemMapper;
    private AmazonS3 s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.url}")
    private String url;

    @Autowired
    public ItemService(ItemRepository itemRepository, TradeRepository tradeRepository, ItemMapper itemMapper, AmazonS3 s3Client, UsersRepository userRepository) {
        this.itemRepository = itemRepository;
        this.tradeRepository = tradeRepository;
        this.itemMapper = itemMapper;
        this.userRepository = userRepository;
        this.s3Client = s3Client;
    }

    public ResponseEntity<String> addItem(ItemCreateDto itemCreateDto) throws IOException  {
        if (itemCreateDto.getFile().getSize()!=0) {
            String fileName = System.currentTimeMillis()+"_"+itemCreateDto.getFile().getOriginalFilename();
            File file = convertMultiPartFileToFile(itemCreateDto.getFile());
            s3Client.putObject(bucketName,fileName, file);
            file.delete();
            String imagePath=this.url+fileName;
            Item item = itemMapper.CreateDtoToItemFile(itemCreateDto, imagePath, itemCreateDto.getUsername());
            itemRepository.save(item);
            logger.info( "Item added with ID: "+item.getId());
            return ResponseEntity.ok("Added");
        }
        else{
            logger.warn( "Item add failed due to missing file");
            return ResponseEntity.badRequest().build();
        }
    }

    public List<ItemDto> getItems() {
        List<Item> items = itemRepository.findAllByStatus(EItemStatus.AVAILABLE);
        if(items.isEmpty()){
            logger.warn("No available items found");
            return null;
        }
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public ResponseEntity<ItemDto> getItemById(Long id) {
        if(itemRepository.existsById(id)) {
            Item item = itemRepository.findById(id).get();
            logger.info("Retrieved item with ID: " + id);
            return ResponseEntity.ok().body(itemMapper.itemToDto(item));
        }
        logger.warn("Item not found with ID: "+ id);
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity updateItem(ItemCreateDto itemCreateDto, Long id) throws IOException {
        if(itemRepository.existsById(id)) {
            Item item = itemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));;
            if(itemCreateDto.isImageChange()) {
                String fileName = System.currentTimeMillis()+"_"+itemCreateDto.getFile().getOriginalFilename();
                File file = convertMultiPartFileToFile(itemCreateDto.getFile());
                s3Client.putObject(bucketName,fileName, file);
                file.delete();
                String imagePath=this.url+fileName;
                item.setImage(imagePath);
            }
            item.setTitle(itemCreateDto.getTitle());
            item.setState(itemCreateDto.getState());
            item.setCategory(ECategory.valueOf(itemCreateDto.getCategory()));
            item.setDescription(itemCreateDto.getDescription());
            item.setStatus(EItemStatus.valueOf(itemCreateDto.getStatus()));
            itemRepository.save(item);
            logger.info( "Item updated with ID: " + id);
            return ResponseEntity.ok("Item updated");
        }
        logger.warn( "Item not found for update with ID: " + id);
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
            logger.info("Item with ID " + id + " deleted.");
            return ResponseEntity.ok().build();
        }
        logger.error("Item with ID " + id + " not found.");
        return ResponseEntity.notFound().build();
    }

    public List<ItemDto> getItemsByCategory(String category) {
        ECategory categoryEnum = ECategory.valueOf(category.toUpperCase());
        List<Item> items = itemRepository.findByCategory(categoryEnum);
        logger.info("Retrieved "+items.size()+" items by category: " +category);
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
    public List<ItemDto> getItemsByUser(String username) {
        Long userId = userRepository.findUserByUsername(username).getId();
        List<Item> items = itemRepository.findByUserId(userId);
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    public List<ItemDto> searchItemsByTitle(String title) {
        List<Item> items = itemRepository.findByTitleContainingIgnoreCaseAndStatus(title, EItemStatus.AVAILABLE);
        if (items.isEmpty()) {
            logger.warn("No items found with title: " + title);
            return null;
        }
        logger.info("Found  "+items.size()+" items by title: " +title);
        return items.stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
     public File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e){
            logger.error("An exception occurred while converting multi-part file to file: " + e.getMessage());
            return null;
        }
        return convertedFile;
    }
}
