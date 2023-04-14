package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.time.LocalDateTime;

@Data
public class TradeDto {

    @NotNull
    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private Long offeredItemId;

    @NotNull
    private EStatus status;

    @Size(max = 300)
    private String comment;

    @NotNull
    private LocalDateTime date;

}
