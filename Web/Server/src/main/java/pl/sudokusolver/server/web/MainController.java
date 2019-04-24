package pl.sudokusolver.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.digitsrecognizers.ANN;
import pl.sudokusolver.recognizerlib.digitsrecognizers.IRecognizer;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.sudokurecognizers.DigitBoxByteSum;
import pl.sudokusolver.recognizerlib.sudokurecognizers.Grid;
import pl.sudokusolver.recognizerlib.sudokurecognizers.SudokuDetector;

@Controller
public class MainController {


    @RequestMapping(value = "/")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(value = "/test")
    public String test(Model model){
        System.out.println("Test");
        Sudoku sudoku = new Sudoku();
        Grid grid = new Grid();
        sudoku.printSudoku();
        return "home";
    }

    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    public String solve(@RequestParam("sudoku")
                                MultipartFile sudoku, Model model){
        //todo: check everything

        return "home";
    }
}
