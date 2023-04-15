package lv.bootcamp.bartersWeb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long id;

    @Column(name = "item_id")
    private Long ItemId;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Item.class)
    @JoinColumn(name = "item_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Item Item;

    @Column(name = "offered_item_id")
    private Long offeredItemId;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Item.class)
    @JoinColumn(name = "offered_item_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Item offeredItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDateTime date;

}
