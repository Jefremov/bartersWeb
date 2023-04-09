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

    @OneToMany(mappedBy = "user")
    private List<Item> items;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
