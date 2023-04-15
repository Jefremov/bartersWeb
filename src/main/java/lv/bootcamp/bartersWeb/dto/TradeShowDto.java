package lv.bootcamp.bartersWeb.dto;

import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.time.LocalDateTime;

@Data
public class TradeShowDto {
    private Long id;

    private Long itemId;
    private String item;

    private Long offeredItemId;

    private String offeredItem;

    private String comment;

    private LocalDateTime date;

    private EStatus status;

}