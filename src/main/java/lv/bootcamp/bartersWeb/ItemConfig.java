package lv.bootcamp.bartersWeb;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.ItemCategory;
import lv.bootcamp.bartersWeb.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemConfig {
    @Bean
    CommandLineRunner commandLineRunner(ItemRepository repository){
        return args -> {
            List<Item> Items= new ArrayList<>();
            Items.add(new Item(1L,"Iphone 10", "old", "Just a great phone to have", "image_path1", ItemCategory.ELECTRONICS));
            Items.add(new Item("Iphone 11", "used", "Only one scratch", "image_path2",ItemCategory.ELECTRONICS));
            Items.add(new Item("Iphone 12", "new", "didnt like the colour:(", "image_path3",ItemCategory.ELECTRONICS));
            Items.add(new Item("Hunger Games Pt3", "used", "Read it, dont need it", "image_path4",ItemCategory.EDUCATIONALMATERIALS));
            Items.add(new Item("Macbook Air 13 2017" , "old", "Good for studies", "image_path5",ItemCategory.ELECTRONICS));
            Items.add(new Item("The NBHD Sweater", "new", "comfy, too big for me", "image_path6",ItemCategory.CLOTHING));
            repository.saveAll(Items);
        };

    }
}

