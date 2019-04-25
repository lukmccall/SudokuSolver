package pl.sudokusolver.server.web;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.sudokurecognizers.Grid;
import pl.sudokusolver.server.bean.Recognizer;
import pl.sudokusolver.server.utility.Utility;

import java.io.IOException;

@RestController
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);

    @Autowired
    private Recognizer recognizer;

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
                                MultipartFile inputImg, Model model){
        //todo: check everything

        LOGGER.trace("Send "+inputImg.getContentType()+" which have " + inputImg.getSize() +" bits");

        try {
            Mat mat = Utility.multipartFileToMat(inputImg);
            Grid grid = new Grid(mat);
            grid.cleanLines();

            return recognizer.getDetector().getSudokuFromGrid(grid).toString();
        } catch (IOException e) {
            // todo: i don't know what i should do next
            return "Error";
        }
    }
}
