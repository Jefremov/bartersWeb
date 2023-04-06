package lv.bootcamp.bartersWeb.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    @GetMapping("/testReact")
    public String testReact() {
        return "React is working!";
    }
}
