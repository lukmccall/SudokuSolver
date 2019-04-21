package pl.sudokusolver.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

@Controller
public class MainController {
    @RequestMapping(value = "/")
    public String home(Model model) {
        return "home";
    }
}
