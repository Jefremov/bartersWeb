package lv.bootcamp.bartersWeb.dto;

import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.time.LocalDateTime;

@Data
public class TradeShowOneDto {
    private Long id;

    private Long itemId;

    private String itemTitle;

    private String itemDescription;

    private String itemState;

    private String itemCategory;

    private String itemUsername;

    private String itemImage;

    private Long offeredItemId;

    private String offeredItemTitle;

    private String offeredItemDescription;

    private String offeredItemState;

    private String offeredItemCategory;

    private String offeredItemUsername;

    private String offeredItemImage;

    private String comment;

    private LocalDateTime date;

    private EStatus status;

}