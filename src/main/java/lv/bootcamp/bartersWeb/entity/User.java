package lv.bootcamp.bartersWeb.entity;


import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private ERole role;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private ArrayList<Item> items;


}
