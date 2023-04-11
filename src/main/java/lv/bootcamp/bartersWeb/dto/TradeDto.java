package lv.bootcamp.bartersWeb.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lv.bootcamp.bartersWeb.entities.EStatus;

import java.util.Date;

@Data
public class TradeDto {

    @NotNull
    private Long id;

    @NotNull
    private String itemId;

    @NotNull
    private String offeredItemId;

    @NotNull
    private EStatus status;

    @Size(max = 300)
    private String comment;

    @NotNull
    private Date date;

}