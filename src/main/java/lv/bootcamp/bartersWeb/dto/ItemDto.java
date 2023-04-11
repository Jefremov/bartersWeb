package lv.bootcamp.bartersWeb.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String title;
    private String image;
    private String description;
    private String category;
    private String state;
    private String status;
    private Long userId;
}
