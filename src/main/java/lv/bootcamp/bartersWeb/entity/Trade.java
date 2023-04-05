package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Data
@Table(name = "trades")
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeId")
    private Long id;
    @OneToOne
    private Item item;
    @Column(name = "offeredItemId")
    private Long offeredItemId;
    @Column(name = "status")
    private String status;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date")
    private Date date;

    public Trade(Long id, Item item, Long offeredItemId, String status, String comment, Date date) {
        this.id = id;
        this.item = item;
        this.offeredItemId = offeredItemId;
        this.status = status;
        this.comment = comment;
        this.date = date;
    }

    public Trade() {
    }
}
