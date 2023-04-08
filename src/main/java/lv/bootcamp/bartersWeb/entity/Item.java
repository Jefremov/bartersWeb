package lv.bootcamp.bartersWeb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Item")
@Data
public class Item {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name="item_sequence",sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "state")
    private String state;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "category")
    private ECategory category;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EItemStatus status;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "userid", insertable=false, updatable=false)
    private User user;
    public Item(Long id, String title, String state, String description, String image, ECategory category, EItemStatus status, Long userId) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.description = description;
        this.image = image;
        this.category = category;
        this.status = status;
        this.userId = userId;
    }
    public Item(String title, String state, String description, String image, ECategory category, EItemStatus status, Long userId) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.image = image;
        this.category = category;
        this.status = status;
        this.userId = userId;
    }

    public Item() {
    }
}
