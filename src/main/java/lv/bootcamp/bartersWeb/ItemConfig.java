package lv.bootcamp.bartersWeb;

import lv.bootcamp.bartersWeb.entity.Item;
import lv.bootcamp.bartersWeb.entity.ECategory;
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
            Items.add(new Item(1L,"Iphone 10", "old", "Just a great phone to have", "src/main/resources/itemimages/iphone10.png", ECategory.ELECTRONICS));
            Items.add(new Item("Iphone 11", "used", "Only one scratch", "src/main/resources/itemimages/iphone11.jpeg", ECategory.ELECTRONICS));
            Items.add(new Item("Iphone 12", "new", "didnt like the colour:(", "src/main/resources/itemimages/iphone12.jpeg", ECategory.ELECTRONICS));
            Items.add(new Item("Hunger Games Pt3", "used", "Read it, dont need it", "src/main/resources/itemimages/hungergames.jpeg", ECategory.EDUCATIONALMATERIALS));
            Items.add(new Item("Macbook Air 13 2017" , "old", "Good for studies", "src/main/resources/itemimages/macbookair.jpeg", ECategory.ELECTRONICS));
            Items.add(new Item("The NBHD Sweater", "new", "comfy, too big for me", "src/main/resources/itemimages/sweater.jpeg", ECategory.CLOTHING));
            repository.saveAll(Items);
        };

    }
}

