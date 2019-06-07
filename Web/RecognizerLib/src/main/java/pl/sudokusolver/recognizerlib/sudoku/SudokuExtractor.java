package pl.sudokusolver.recognizerlib.sudoku;

import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.IFilter;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;

import java.util.List;

/**
 * This class connect all levels of extracting sudoku img in one abstract component.<br>
 * It contains all functionality to be able to control process of extraction.
 */
public abstract class SudokuExtractor implements ISudokuExtractor {
    /**
     * grid extract algorithm.
     */
    protected GridExtractStrategy gridExtractStrategy;

    /**
     * cells extract algorithm.
     */
    protected CellsExtractStrategy cellsExtractStrategy;

    /**
     * digits extract algorithm.
     */
    protected DigitsExtractStrategy digitsExtractStrategy;

    /**
     * orc.
     */
    protected IRecognizer recognizer;

    /**
     * filters which are apply before grid extraction.
     */
    protected List<IFilter> preGridFilters;

    /**
     * filters which are apply before cells extraction.
     */
    protected List<IFilter> preCellsFilters;

    /**
     * filters which are apply before digit extraction.
     */
    protected List<IFilter> preDigitsFilters;

    /**
     * Create object form given parameters.
     * @param gridExtractStrategy gridExtractStrategy
     * @param cellsExtractStrategy cellsExtractStrategy
     * @param digitsExtractStrategy digitsExtractStrategy
     * @param recognizer recognizer
     */
    protected SudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                           DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer) {
        this.gridExtractStrategy = gridExtractStrategy;
        this.cellsExtractStrategy = cellsExtractStrategy;
        this.digitsExtractStrategy = digitsExtractStrategy;
        this.recognizer = recognizer;
    }

    /**
     * Create object form given parameters.
     * @param gridExtractStrategy gridExtractStrategy
     * @param cellsExtractStrategy cellsExtractStrategy
     * @param digitsExtractStrategy digitsExtractStrategy
     * @param recognizer recognizer
     * @param preGridFilters preGridFilters
     * @param preCellsFilters preCellsFilters
     * @param preDigitsFilters preDigitsFilters
     */
    protected SudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
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

    /**
     * @return currently using extraction strategy
     */
    public GridExtractStrategy getGridExtractStrategy() {
        return gridExtractStrategy;
    }

    /**
     * @param gridExtractStrategy new extraction strategy
     */
    public void setGridExtractStrategy(GridExtractStrategy gridExtractStrategy) {
        this.gridExtractStrategy = gridExtractStrategy;
    }

    /**
     * @return currently using cells extraction strategy
     */
    public CellsExtractStrategy getCellsExtractStrategy() {
        return cellsExtractStrategy;
    }

    /**
     * @param gridExtractStrategy new extraction strategy
     */
    public void setCellsExtractStrategy(CellsExtractStrategy cellsExtractStrategy) {
        this.cellsExtractStrategy = cellsExtractStrategy;
    }

    /**
     * @return currently using digits extraction strategy
     */
    public DigitsExtractStrategy getDigitsExtractStrategy() {
        return digitsExtractStrategy;
    }

    /**
     * @param gridExtractStrategy new extraction strategy
     */
    public void setDigitsExtractStrategy(DigitsExtractStrategy digitsExtractStrategy) {
        this.digitsExtractStrategy = digitsExtractStrategy;
    }

    /**
     * @return currently using ocr
     */
    public IRecognizer getRecognizer() {
        return recognizer;
    }

    /**
     * @param gridExtractStrategy new ocr
     */
    public void setRecognizer(IRecognizer recognizer) {
        this.recognizer = recognizer;
    }
}
