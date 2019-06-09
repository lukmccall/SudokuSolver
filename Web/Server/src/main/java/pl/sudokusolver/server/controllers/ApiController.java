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

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * Api controller.
 */
@RestController
@RequestMapping("/api/")
public class ApiController {
    /**
     * logger.
     */
    @Autowired
    private Logger LOGGER;

    /**
     * ocr getter.
     */
    @Autowired
    private DigitRecognizer digitRecognizer;

    /**
     * solving algorithm.
     */
    @Autowired
    private ISolver solver;

    /**
     * Only consume json. Only post requests.
     * @param json json with sudoku.
     * @return json from <code>GridModel</code>
     * @throws MissingServletRequestParameterException if json is invalid or sudoku is missing.
     * @throws SolvingFailedException if solving algorithm couldn't solve sudoku.
     */
    @RequestMapping(value = "/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String solve(@RequestBody String json) throws MissingServletRequestParameterException, SolvingFailedException {

        // get sudoku from json
        Sudoku sudoku = new Gson().fromJson(json, Sudoku.class);

        // it was valid?
        if(sudoku == null)
            throw new MissingServletRequestParameterException("sudoku", "int[9][9]");

        // try to solve
        if(!solver.solve(sudoku.getGrid()))
            throw new SolvingFailedException();

        // generating response
        return new Gson().toJson(new GridModel(1, sudoku.getGrid()));
    }

    /**
     * Only post requests.
     * @param inputImg inputImg
     * @param lineTreshold lineTreshold
     * @param minLineSize minLineSize
     * @param lineGap lineGap
     * @param blurSize blurSize
     * @param blurBlockSize blurBlockSize
     * @param blurC blurC
     * @param scaling scaling
     * @param recognizer recognizer
     * @param strictMode strictMode
     * @return json response from <code>GridModel</code>
     * @throws IllegalArgumentException if <code>inputImg</code> is missing.
     * @throws IOException if <code>inputImg</code> was corrupted.
     * @throws NotFoundSudokuException if couldn't found sudoku.
     * @throws CellsExtractionFailedException if couldn't extract cells.
     * @throws DigitExtractionFailedException if couldn't digits digits.
     */
    @RequestMapping(value = "/extractfromimg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String extract(
            @RequestParam("sudoku") MultipartFile inputImg,
            @RequestParam(value = "lineThreshold", required = false, defaultValue = "50") int lineTreshold,
            @RequestParam(value = "minLineSize", required = false, defaultValue = "65") int minLineSize,
            @RequestParam(value = "lineGap", required = false, defaultValue = "5") int lineGap,
            @RequestParam(value = "blurSize", required = false, defaultValue = "3") int blurSize,
            @RequestParam(value = "blurBlockSize", required = false, defaultValue = "31") int blurBlockSize,
            @RequestParam(value = "blurC", required = false, defaultValue = "15") int blurC,
            @RequestParam(value = "scaling", required = false, defaultValue = "FIXED WIDTH SCALING") String scaling,
            @RequestParam(value = "recognizer", required = false, defaultValue = "SVM") String recognizer,
            @RequestParam(value = "strictMode", required = false, defaultValue = "false") boolean strictMode
    ) throws IllegalArgumentException, IOException, NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException {

        LOGGER.trace("Get "+inputImg.getContentType()+" which have " + inputImg.getSize() +" bits");

        // checking if img is valid
        if(!inputImg.getContentType().equals("image/jpeg") && !inputImg.getContentType().equals("image/png")
                && !inputImg.getContentType().equals("image/jpg"))
            throw new IllegalArgumentException("Expected jpg or png get " + inputImg.getContentType());

//        LOGGER.trace("Parameters:\nlineThreshold: " + lineTreshold
//                                 + "\nminLineSize " + minLineSize
//                                 + "\nlineGap " + lineGap
//                                 + "\nblurSize " + blurSize
//                                 +"\nblurBlockSize " + blurBlockSize
//                                 +"\nblurC " + blurC
//                                 +"\nscaling " + scaling
//                                 +"\nrecognizer " + recognizer
//                                 +"\nstrictMode " + strictMode);

        // building base sudoku extractor
        BaseSudokuExtractor.Builder builder  = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(digitRecognizer.getRecognizer(recognizer, strictMode))
                .addPreGridFilters(new MaxResizeFilter(new Size(2500,2500)))
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(lineTreshold, minLineSize, lineGap,
                                                        new MedianBlur(blurSize, blurBlockSize, blurC)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)));


        // adding scaling
        if (scaling.equals("FIXED WIDTH SCALING"))
            builder.addPreGridFilters(new FixedWidthResizeFilter());
        else if(scaling.equals("MAX AXIS RESIZE"))
            builder.addPreGridFilters(new MaxResizeFilter());
        else if(scaling.equals("NONE"))
            builder.addPreGridFilters(new MaxResizeFilter(new Size(1500,1500)));

        SudokuExtractor baseSudokuExtractor = builder.build();

        // loading img to matrix
        Mat mat = Utility.multipartFileToMat(inputImg);
        // extracting
        Sudoku sudoku = baseSudokuExtractor.extract(mat);
        // cleaning
        mat.release();

        // check if sudoku img was valid
        if(sudoku.getGrid() == null || sudoku.empty())
            throw new NotFoundSudokuException();


        // generating response
        return new Gson().toJson(new GridModel(1, sudoku.getGrid()));
    }
}
