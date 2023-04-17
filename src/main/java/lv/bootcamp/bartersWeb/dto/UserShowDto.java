package lv.bootcamp.bartersWeb.dto;

import lombok.Data;
import lv.bootcamp.bartersWeb.entities.ERole;
import lv.bootcamp.bartersWeb.entities.Item;

import java.util.List;

@Data
public class UserShowDto {

    private Long id;
    private String username;
    private ERole role;
    private String description;
    private String email;
    private String phoneNumber;
    private List<Item> items;

}
