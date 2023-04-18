package lv.bootcamp.bartersWeb.mappers;

import lv.bootcamp.bartersWeb.dto.ItemCreateDto;
import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.dto.ItemOwnerDto;
import lv.bootcamp.bartersWeb.entities.ECategory;
import lv.bootcamp.bartersWeb.entities.EItemStatus;
import lv.bootcamp.bartersWeb.entities.Item;
import lv.bootcamp.bartersWeb.services.ReviewService;
import lv.bootcamp.bartersWeb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ItemMapper {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ReviewService reviewService;
    public ItemDto itemToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setImage(item.getImage());
        dto.setDescription(item.getDescription());
        dto.setCategory(item.getCategory().getDisplayName());
        dto.setState(item.getState());
        dto.setDate(item.getDate());
        dto.setStatus(item.getStatus().getDisplayName());
        dto.setUserId(item.getUserId());
        return dto;
    }
    public ItemOwnerDto itemToDtoOwner(Item item) {
        ItemOwnerDto dto = new ItemOwnerDto();
        dto.setUsername(usersRepository.findById(item.getUserId()).get().getUsername());
        dto.setRating(reviewService.getAverageRatingForUser(dto.getUsername()));
        return dto;
    }

    public Item CreateDtoToItemFile(ItemCreateDto itemCreateDto,String filename, String username){
        Item item = new Item();
        item.setTitle(itemCreateDto.getTitle());
        item.setImage(filename);
        item.setDescription(itemCreateDto.getDescription());
        item.setCategory(ECategory.valueOf(itemCreateDto.getCategory()));
        item.setState(itemCreateDto.getState());
        item.setDate(LocalDateTime.now());
        item.setStatus(EItemStatus.AVAILABLE);
        item.setUserId(usersRepository.findUserByUsername(username).getId());
        return item;
    }
}