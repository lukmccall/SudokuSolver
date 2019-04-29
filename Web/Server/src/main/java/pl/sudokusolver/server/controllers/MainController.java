package pl.sudokusolver.server.controllers;

import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.sudokurecognizers.GridImg;
import pl.sudokusolver.server.bean.Recognizer;
import pl.sudokusolver.server.utility.Utility;
import pl.sudokusolver.solver.BrutalSolver;
import pl.sudokusolver.solver.ISolver;

import java.io.IOException;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@RestController
public class MainController {
    @Autowired
    private Logger LOGGER;

    @Autowired
    private Recognizer recognizer;

    @RequestMapping(value = "/")
    public String home() {
        Sudoku sudoku = new Sudoku();
        return new Gson().toJson(sudoku);


    }

    @RequestMapping(value = "/test")
    public String test(){
        System.out.println("Test");
        Sudoku sudoku = new Sudoku();
        GridImg gridImg = new GridImg();
        sudoku.printSudoku();
        return "home";
    }

    @RequestMapping(value = "/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String solve(@RequestBody String json) throws MissingServletRequestParameterException {

        Sudoku sudoku = new Gson().fromJson(json, Sudoku.class);
        if(sudoku == null || sudoku.empty())
            throw new MissingServletRequestParameterException("sudoku", "int[9][9]");
        ISolver solver = new BrutalSolver();
        solver.solve(sudoku.getGrid());
        return sudoku.toString();
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    public String solve(@RequestParam("sudoku")
                                MultipartFile inputImg){
        //todo: check everything

        LOGGER.trace("Send "+inputImg.getContentType()+" which have " + inputImg.getSize() +" bits");

        try {
            Mat mat = Utility.multipartFileToMat(inputImg);
            GridImg gridImg = new GridImg(mat);

            return recognizer.getDetector().getSudokuFromGrid(gridImg).toString();
        } catch (IOException e) {
            // todo: i don't know what i should do next
            return "Error";
        } catch (NotFoundSudokuExceptions notFoundSudokuExceptions) {
           return  "Not found sudoku";
        }
    }
}
