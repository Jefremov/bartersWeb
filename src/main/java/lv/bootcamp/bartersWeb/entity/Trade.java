package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeid")
    private Long id;

    @OneToOne
    private Item item;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Date date;

}
