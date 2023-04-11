package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "trades")
public class Trade {

    private enum TradeStatus {
        PENDING("Pending"),
        ACCEPTED("Accepted"),
        REJECTED("Rejected");

        private final String displayName;

        TradeStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_Id")
    private Item item;

    @Column(name = "offeredItem_Id")
    private Long offeredItemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TradeStatus status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Date date;

}
