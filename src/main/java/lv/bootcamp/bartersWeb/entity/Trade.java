package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeid")
    private Long id;

}
