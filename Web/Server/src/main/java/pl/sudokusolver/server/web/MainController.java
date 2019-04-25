package pl.sudokusolver.server.web;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
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
import pl.sudokusolver.server.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imdecode;
import static org.opencv.imgcodecs.Imgcodecs.imread;

@Controller
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);


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

        LOGGER.trace("Send img which have " + inputImg.getSize() +" bits");
        try {
            Mat mat = Utility.multipartFileToMat(inputImg);

        } catch (IOException e) {
            // todo: i don't know what i should do next
        }

        return "home";
    }
}
