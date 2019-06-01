package pl.sudokusolver.recognizerlib.sudoku;

import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.IFilter;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;

import java.util.List;

/**
 * Podstawowa klasa udostępniana użytkowikowi. Lączy ona wszystkie funckjionalności.
 * Umożliwa w pelni personalizację poszczególnych kroków extrakcji sudoku oraz zmiany używanych filtrów.
 */
public abstract class SudokuExtractor implements ISudokuExtractor {
    protected GridExtractStrategy gridExtractStrategy;
    protected CellsExtractStrategy cellsExtractStrategy;
    protected DigitsExtractStrategy digitsExtractStrategy;
    protected IRecognizer recognizer;

    protected List<IFilter> preGridFilters;
    protected List<IFilter> preCellsFilters;
    protected List<IFilter> preDigitsFilters;

    protected SudokuExtractor(GridExtractStrategy gridExtractStrategy, CellsExtractStrategy cellsExtractStrategy,
                           DigitsExtractStrategy digitsExtractStrategy, IRecognizer recognizer) {
        this.gridExtractStrategy = gridExtractStrategy;
        this.cellsExtractStrategy = cellsExtractStrategy;
        this.digitsExtractStrategy = digitsExtractStrategy;
        this.recognizer = recognizer;
    }

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

    public GridExtractStrategy getGridExtractStrategy() {
        return gridExtractStrategy;
    }

    public void setGridExtractStrategy(GridExtractStrategy gridExtractStrategy) {
        this.gridExtractStrategy = gridExtractStrategy;
    }

    public CellsExtractStrategy getCellsExtractStrategy() {
        return cellsExtractStrategy;
    }

    public void setCellsExtractStrategy(CellsExtractStrategy cellsExtractStrategy) {
        this.cellsExtractStrategy = cellsExtractStrategy;
    }

    public DigitsExtractStrategy getDigitsExtractStrategy() {
        return digitsExtractStrategy;
    }

    public void setDigitsExtractStrategy(DigitsExtractStrategy digitsExtractStrategy) {
        this.digitsExtractStrategy = digitsExtractStrategy;
    }

    public IRecognizer getRecognizer() {
        return recognizer;
    }

    public void setRecognizer(IRecognizer recognizer) {
        this.recognizer = recognizer;
    }
}
