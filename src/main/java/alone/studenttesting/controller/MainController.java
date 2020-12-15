package alone.studenttesting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

        @GetMapping("/home")
        public String home() {
            return "home";
        }

        @GetMapping("/international")
        public String getInternationalPage() {
        return "home";
    }

    }


