package lv.bootcamp.bartersWeb.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDto {
    private Long id;

    private String title;

    private String image;

    private String description;

    private String category;

    private String state;

    private String status;

    private LocalDateTime date;

    private Long userId;

}
