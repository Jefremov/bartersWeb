package lv.bootcamp.bartersWeb.entities;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemid")
    private Long id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="user_id")
    private User user;

    private String condition;

    private String description;

    private String image;

    private String category;
}