package lv.bootcamp.bartersWeb.dto;

import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.time.LocalDateTime;

@Data
public class TradeShowOneDto {
    private Long id;

    private Long itemId;

    private Long offeredItemId;

    private String comment;

    private LocalDateTime date;

    private EStatus status;

}