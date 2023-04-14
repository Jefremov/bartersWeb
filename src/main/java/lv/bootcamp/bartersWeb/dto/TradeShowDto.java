package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;
import lv.bootcamp.bartersWeb.entities.Item;

import java.time.LocalDateTime;

@Data
public class TradeShowDto {
    private Long id;

    private Long itemId;

    private Item item;

    private Long offeredItemId;

    private String comment;

    private LocalDateTime date;

    private EStatus status;

}