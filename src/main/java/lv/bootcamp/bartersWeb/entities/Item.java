package lv.bootcamp.bartersWeb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
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
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "user_id")
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable=false, updatable=false)
    private User user;
    public Item(Long id, String title, String state, String description, String image, ECategory category, EItemStatus status, Long userId, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.description = description;
        this.image = image;
        this.category = category;
        this.status = status;
        this.date = date;
        this.userId = userId;
    }
    public Item(String title, String state, String description, String image, ECategory category, EItemStatus status, Long userId, LocalDateTime date) {
        this.title = title;
        this.state = state;
        this.description = description;
        this.image = image;
        this.category = category;
        this.status = status;
        this.userId = userId;
        this.date = date;
    }

    public Item() {
    }
}
