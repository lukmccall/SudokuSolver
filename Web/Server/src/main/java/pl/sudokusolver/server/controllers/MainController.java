package pl.sudokusolver.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main controller. Used only for detect if server working.
 */
@Controller
public class MainController {
    /**
     * @return home page
     */
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
