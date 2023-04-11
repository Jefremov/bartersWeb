package lv.bootcamp.bartersWeb.service.mapper;

import lv.bootcamp.bartersWeb.dto.ItemDto;
import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.EItemStatus;
import lv.bootcamp.bartersWeb.entity.ECategory;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDto itemToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setTitle(item.getTitle());
        dto.setImage(item.getImage());
        dto.setDescription(item.getDescription());
        dto.setCategory(item.getCategory().getDisplayName());
        dto.setState(item.getState());
        dto.setStatus(item.getStatus().getDisplayName());
        dto.setUserId(item.getUserId());
        return dto;
    }
}