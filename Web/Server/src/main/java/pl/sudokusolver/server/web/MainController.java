package pl.sudokusolver.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

@Controller
public class MainController {
    @RequestMapping(value = "/")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = "solve", method = RequestMethod.POST)
    public String solve(@RequestParam("sudoku")
                                MultipartFile sudoku, Model model){
        //todo: check everything

        return "home";
    }
}
