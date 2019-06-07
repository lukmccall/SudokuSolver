package pl.sudokusolver.recognizerlib.sudoku;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.IFilter;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgcodecs.Imgcodecs.imread;

/**
 * Base sudoku extractor controller.<br>
 * Extraction algorithm:<br>
 *     <ul>
 *         <li>Apply <code>preGridFilters</code> to input matrix</li>
 *         <li>Using <code>gridExtractStrategy</code> extracting grid</li>
 *         <li>Apply <code>preCellsFilters</code> to grid</li>
 *         <li>For each cell apply <code>preDigitsFilters</code> to send output to <code>recognizer</code></li>
 *     </ul>
 */
public class BaseSudokuExtractor extends SudokuExtractor {

    public BaseSudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                               DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer){
        super(gridExtractStrategy,cellsExtractStrategy,digitsExtractStrategy,recognizer);
    }

    public BaseSudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                               DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer,
                               List<IFilter> preGridFilters, List<IFilter> preCellsFilters,
                               List<IFilter> preDigitsFilters) {
        super(gridExtractStrategy, cellsExtractStrategy, digitsExtractStrategy, recognizer,
                preGridFilters, preCellsFilters, preDigitsFilters);
    }


    @Override
    public Sudoku extract(String url) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException {
        Mat img = imread(url);
        return extract(img);
    }

    @Override
    public Sudoku extract(Mat img) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException {
        Utility.applyFilters(img, preGridFilters);
        Mat sudokuGrid = getGridExtractStrategy().extract(img);

        // check if grid is big enough
        if (sudokuGrid.height() * sudokuGrid.width() <= 40000) throw new NotFoundSudokuException("Sudoku jest za małe.");

        Utility.applyFilters(sudokuGrid, preCellsFilters);
        List<Mat> cells = getCellsExtractStrategy().extract(sudokuGrid);

        // check if calls are correctly cut
        if(cells == null) throw new CellsExtractionFailedException();

        Sudoku sudoku = new Sudoku();
        for(int i = 0; i < cells.size(); i++){
            Mat cell = cells.get(i);
            Utility.applyFilters(cell, preDigitsFilters);

            // get single digit
            Optional<Mat> digit = getDigitsExtractStrategy().extract(cell);

            // found digit?
            if (digit.isPresent()) {
                // try to rec
                sudoku.setDigit(getRecognizer().recognize(digit.get()).getFirst(), i / 9, i % 9);
                digit.get().release();
            }
            cells.get(i).release();
        }

        sudokuGrid.release();
        return sudoku;
    }

    /**
     * @return builder for this class.
     */
    public static Builder builder() {
        return new Builder();
    }


    /**
     * Builder for <code>BaseSudokuExtractor</code>
     */
    public static final class Builder {
        private GridExtractStrategy gridExtractStrategy;
        private CellsExtractStrategy cellsExtractStrategy;
        private DigitsExtractStrategy digitsExtractStrategy;
        private IRecognizer recognizer;

        private List<IFilter> preGridFilters;
        private List<IFilter> preCellsFilters;
        private List<IFilter> preDigitsFilters;

        /**
         * @return sudoku extractor.
         * @throws IllegalStateException if couldn't create object from given parameters.
         */
        public SudokuExtractor build() throws IllegalStateException {
            if(gridExtractStrategy == null){
                throw new IllegalStateException("GridExtractStrategy cannot be null");
            }
            if(cellsExtractStrategy == null){
                throw new IllegalStateException("CellsExtractStrategy cannot be null");
            }
            if(digitsExtractStrategy == null){
                throw new IllegalStateException("DigitsExtractStrategy cannot be null");
            }
            if(recognizer == null){
                throw new IllegalStateException("Recognizer cannot be null");
            }

            return new BaseSudokuExtractor(gridExtractStrategy, cellsExtractStrategy, digitsExtractStrategy,
                    recognizer, preGridFilters, preCellsFilters, preDigitsFilters);
        }

        /**
         * @param gridExtractStrategy gridExtractStrategy
         * @return builder
         */
        public Builder setGridStrategy(GridExtractStrategy gridExtractStrategy) {
            this.gridExtractStrategy = gridExtractStrategy;
            return this;
        }

        /**
         * @param cellsExtractStrategy cellsExtractStrategy
         * @return builder
         */
        public Builder setCellsStrategy(CellsExtractStrategy cellsExtractStrategy) {
            this.cellsExtractStrategy = cellsExtractStrategy;
            return this;
        }

        /**
         * @param digitsExtractStrategy digitsExtractStrategy
         * @return builder
         */
        public Builder setDigitsStrategy(DigitsExtractStrategy digitsExtractStrategy) {
            this.digitsExtractStrategy = digitsExtractStrategy;
            return this;
        }

        /**
         * @param recognizer recognizer
         * @return builder
         */
        public Builder setRecognizer(IRecognizer recognizer) {
            this.recognizer = recognizer;
            return this;
        }


        /**
         * @param filter new filter
         * @return builder
         */
        public Builder addPreGridFilters(IFilter filter){
            if(preGridFilters == null){
                preGridFilters = new LinkedList<>();
                preGridFilters.add(filter);
            }
            else
                preGridFilters.add(filter);
            return this;
        }

        /**
         * @param filter new filter
         * @return builder
         */
        public Builder addPreCellsFilters(IFilter filter){
            if(preCellsFilters == null){
                preCellsFilters = new LinkedList<>();
                preCellsFilters.add(filter);
            }
            else
                preCellsFilters.add(filter);
            return this;
        }

        /**
         * @param filter new filter
         * @return builder
         */
        public Builder addPreDigitsFilters(IFilter filter){
            if(preDigitsFilters == null){
                preDigitsFilters = new LinkedList<>();
                preDigitsFilters.add(filter);
            }
            else
                preDigitsFilters.add(filter);
            return this;
        }
    }
}
