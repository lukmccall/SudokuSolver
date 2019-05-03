package pl.sudokusolver.server.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private Logger LOGGER;

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
