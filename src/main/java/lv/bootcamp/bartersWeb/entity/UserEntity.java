package lv.bootcamp.bartersWeb.entity;


import jakarta.persistence.*;
import lombok.Data;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Item> items;


}
