package pl.sudokusolver.server.controllers;

import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.sudoku.BaseSudokuExtractor;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.sudoku.SudokuExtractor;
import pl.sudokusolver.server.bean.DigitRecognizer;
import pl.sudokusolver.server.exceptions.SolvingFailedException;
import pl.sudokusolver.server.models.GridModel;
import pl.sudokusolver.server.utility.Utility;
import pl.sudokusolver.solver.ISolver;

import java.io.IOException;

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
    public String extract(
            @RequestParam("sudoku") MultipartFile inputImg,
            @RequestParam(value = "lineThreshold", required = false, defaultValue = "50") int lineTreshold,
            @RequestParam(value = "minLineSize", required = false, defaultValue = "100") int minLineSize,
            @RequestParam(value = "lineGap", required = false, defaultValue = "5") int lineGap,
            @RequestParam(value = "blurSize", required = false, defaultValue = "3") int blurSize,
            @RequestParam(value = "blurBlockSize", required = false, defaultValue = "31") int blurBlockSize,
            @RequestParam(value = "blurC", required = false, defaultValue = "15") int blurC,
            @RequestParam(value = "scaling", required = false, defaultValue = "Fixed Width Resize") String scaling,
            @RequestParam(value = "recognizer", required = false, defaultValue = "SVM") String recognizer,
            @RequestParam(value = "strictMode", required = false, defaultValue = "false") boolean strictMode
    ) throws IllegalArgumentException, IOException, NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException {

        LOGGER.trace("Get "+inputImg.getContentType()+" which have " + inputImg.getSize() +" bits");

        if(!inputImg.getContentType().equals("image/jpeg") && !inputImg.getContentType().equals("image/png")
                && !inputImg.getContentType().equals("image/jpg"))
            throw new IllegalArgumentException("Expected jpg or png get " + inputImg.getContentType());


        BaseSudokuExtractor.Builder builder  = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(digitRecognizer.getRecognizer(recognizer, strictMode))
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new CleanLinesFilter(lineTreshold, minLineSize, lineGap,
                                                        new MedianBlur(blurSize, blurBlockSize, blurC)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)));


        if (scaling.equals("FIXED WIDTH SCALING"))
            builder.addPreGridFilters(new FixedWidthResizeFilter());
        else if(scaling.equals("MAX AXIS RESIZE"))
            builder.addPreDigitsFilters(new MaxResizeFilter());
        else if(scaling.equals("NONE"))
            builder.addPreDigitsFilters(new MaxResizeFilter(new Size(1500,1500)));

        SudokuExtractor baseSudokuExtractor = builder.build();

        Mat mat = Utility.multipartFileToMat(inputImg);
        Sudoku sudoku = baseSudokuExtractor.extract(mat);
        mat.release();
        return new Gson().toJson(new GridModel(1, sudoku.getGrid()));
    }
}
