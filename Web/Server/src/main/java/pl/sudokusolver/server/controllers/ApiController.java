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
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.BlurFilter;
import pl.sudokusolver.recognizerlib.filters.CleanLinesFilter;
import pl.sudokusolver.recognizerlib.filters.MaxResizeFilter;
import pl.sudokusolver.recognizerlib.filters.NotFilter;
import pl.sudokusolver.recognizerlib.sudoku.BaseSudokuExtractor;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.server.bean.DigitRecognizer;
import pl.sudokusolver.server.exceptions.SolvingFailedException;
import pl.sudokusolver.server.models.GridModel;
import pl.sudokusolver.server.utility.Utility;
import pl.sudokusolver.solver.ISolver;

import java.io.IOException;
import java.util.Collections;

import static org.opencv.highgui.HighGui.waitKey;

@RestController
@RequestMapping("/api/")
public class ApiController {
    @Autowired
    private Logger LOGGER;

    @Autowired
    private DigitRecognizer digitRecognizer;

    @Autowired
    private ISolver solver;

    @RequestMapping(value = "/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String solve(@RequestBody String json) throws MissingServletRequestParameterException, SolvingFailedException {

        Sudoku sudoku = new Gson().fromJson(json, Sudoku.class);
        if(sudoku == null || sudoku.empty())
            throw new MissingServletRequestParameterException("sudoku", "int[9][9]");

        if(!solver.solve(sudoku.getGrid()))
            throw new SolvingFailedException();

        return new Gson().toJson(new GridModel(1, sudoku.getGrid()));
    }

    @RequestMapping(value = "/extractfromimg", method = RequestMethod.POST)
    public String solve(
            @RequestParam("sudoku") MultipartFile inputImg,
            @RequestParam(value = "lineThreshold", required = false, defaultValue = "80") int lineTreshold,
            @RequestParam(value = "minLineSize", required = false, defaultValue = "200") int minLineSize,
            @RequestParam(value = "lineGap", required = false, defaultValue = "20") int lineGap,
            @RequestParam(value = "blurSize", required = false, defaultValue = "11") int blurSize,
            @RequestParam(value = "blurBlockSize", required = false, defaultValue = "5") int blurBlockSize,
            @RequestParam(value = "blurC", required = false, defaultValue = "2") int blurC
    ) throws IllegalArgumentException, IOException, NotFoundSudokuException, CellsExtractionFailedException {

        LOGGER.trace("Send "+inputImg.getContentType()+" which have " + inputImg.getSize() +" bits");

        if(!inputImg.getContentType().equals("image/jpeg") && !inputImg.getContentType().equals("image/png")
                && !inputImg.getContentType().equals("image/jpg"))
            throw new IllegalArgumentException("Expected jpg or png get " + inputImg.getContentType());

        BaseSudokuExtractor baseSudokuExtractor = new BaseSudokuExtractor(
            new DefaultGridExtractStrategy(new BlurFilter(blurSize,blurBlockSize,blurC)),
            new SizeCellsExtractStrategy(),
            new FastDigitExtractStrategy(),
            digitRecognizer.getRecognizer(),
            Collections.singletonList(new MaxResizeFilter()),
            Collections.singletonList(new CleanLinesFilter(lineTreshold, minLineSize, lineGap)),
            null
        );


        Mat mat = Utility.multipartFileToMat(inputImg);
        Sudoku sudoku = baseSudokuExtractor.extract(mat);
        return new Gson().toJson(new GridModel(1, sudoku.getGrid()));
    }
}
