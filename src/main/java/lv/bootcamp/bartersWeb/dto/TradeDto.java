package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.time.LocalDateTime;

@Data
public class TradeDto {

    @NotNull(message = "No item has been picked")
    private Long itemId;

    @NotNull(message = "Must select an item for the trade")
    private Long offeredItemId;

    @Size(max = 300)
    @Size(min = 5, message = "Comment must be at least 5 characters")
    private String comment;

}
