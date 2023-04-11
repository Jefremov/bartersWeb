//package lv.bootcamp.bartersWeb.configs;
//
//import lv.bootcamp.bartersWeb.entities.Item;
//import lv.bootcamp.bartersWeb.entities.User;
//import lv.bootcamp.bartersWeb.entities.ECategory;
//import lv.bootcamp.bartersWeb.entities.EItemStatus;
//import lv.bootcamp.bartersWeb.repositories.ItemRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class ItemConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(ItemRepository repository){
//        User user = new User();
//        user.setId(1L);
//        return args -> {
//            List<Item> Items= new ArrayList<>();
//            Items.add(new Item(1L,"Iphone 10", "old", "Just a great phone to have", "src/main/resources/itemimages/iphone10.png", ECategory.ELECTRONICS, EItemStatus.UNAVAILABLE, 1L));
//            Items.add(new Item("Iphone 11", "used", "Only one scratch", "src/main/resources/itemimages/iphone11.jpeg", ECategory.ELECTRONICS, EItemStatus.AVAILABLE, 1L));
//            Items.add(new Item("Iphone 12", "new", "didnt like the colour:(", "src/main/resources/itemimages/iphone12.jpeg", ECategory.ELECTRONICS, EItemStatus.AVAILABLE, 1L));
//            Items.add(new Item("Hunger Games Pt3", "used", "Read it, dont need it", "src/main/resources/itemimages/hungergames.jpeg", ECategory.EDUCATIONALMATERIALS, EItemStatus.AVAILABLE, 1L));
//            Items.add(new Item("Macbook Air 13 2017" , "old", "Good for studies", "src/main/resources/itemimages/macbookair.jpeg", ECategory.ELECTRONICS, EItemStatus.AVAILABLE, 1L));
//            Items.add(new Item("The NBHD Sweater", "new", "comfy, too big for me", "src/main/resources/itemimages/sweater.jpeg", ECategory.CLOTHING, EItemStatus.AVAILABLE, 1L));
//            repository.saveAll(Items);
//        };
//
//    }
//}
//
