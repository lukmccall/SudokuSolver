package pl.sudokusolver.recognizerlib.sudoku;

import org.opencv.core.*;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.IFilter;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.util.List;
import java.util.Optional;

import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.cvtColor;

public class SudokuExtractor {
    private GridExtractStrategy gridExtractStrategy;
    private CellsExtractStrategy cellsExtractStrategy;
    private DigitsExtractStrategy digitsExtractStrategy;
    private IRecognizer recognizer;

    private List<IFilter> preGridFilters;
    private List<IFilter> preCellsFilters;
    private List<IFilter> preDigitsFilters;


    private static boolean containsDigit (Mat input) {
        double tl = input.size().height/3;
        double br = input.size().width - input.size().width/3;

        Rect cut = new Rect(new Point(tl,tl), new Point(br,br));

        return Core.countNonZero(new Mat(input, cut)) > 20;
    }


    public SudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                            DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer){
        this.gridExtractStrategy = gridExtractStrategy;
        this.cellsExtractStrategy = cellsExtractStrategy;
        this.digitsExtractStrategy = digitsExtractStrategy;
        this.recognizer = recognizer;

        this.preCellsFilters = null;
        this.preDigitsFilters = null;
        this.preGridFilters = null;
    }

    public SudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                           DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer,
                           List<IFilter> preGridFilters, List<IFilter> preCellsFilters,
                           List<IFilter> preDigitsFilters) {
        this.gridExtractStrategy = gridExtractStrategy;
        this.cellsExtractStrategy = cellsExtractStrategy;
        this.digitsExtractStrategy = digitsExtractStrategy;

        this.recognizer = recognizer;
        this.preGridFilters = preGridFilters;
        this.preCellsFilters = preCellsFilters;
        this.preDigitsFilters = preDigitsFilters;
    }

    public Sudoku extract(String url) throws NotFoundSudokuExceptions {
        Mat img = imread(url);

        Utility.applyFilters(img, preGridFilters);
        Mat sudokuGrid = gridExtractStrategy.extractGrid(img);

        Utility.applyFilters(sudokuGrid, preCellsFilters);
        List<Mat> cells = cellsExtractStrategy.extract(sudokuGrid);

        //todo: make exception
        if(cells == null) return new Sudoku();

        Sudoku sudoku = new Sudoku();
        for(int i = 0; i < cells.size(); i++){
            Mat cell = cells.get(i);
            Utility.applyFilters(cell, preDigitsFilters);
            Optional<Mat> digit = digitsExtractStrategy.extractDigit(cell);

            if (digit.isPresent())
                sudoku.setDigit(recognizer.recognize(digit.get()).getFirst(), i/9, i%9);
        }
        return sudoku;

    }

}
