package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemid")
    private Long id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="user_id")
    private UserEntity userEntity;
}
