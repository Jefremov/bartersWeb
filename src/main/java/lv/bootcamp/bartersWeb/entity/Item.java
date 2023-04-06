package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Item")
@Getter
@Setter
public class Item {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="item_sequence",sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private String status;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "category")
    private ECategory category;

    public Item(Long id, String title, String status, String description, String image, ECategory category) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
        this.image = image;
        this.category = category;
    }
    public Item(String title, String status, String description, String image, ECategory category) {
        this.title = title;
        this.status = status;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Item() {
    }
}
