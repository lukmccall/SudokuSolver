package pl.sudokusolver.recognizerlib.sudoku;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

/**
 * Base interface used by server.<br>
 * Connect all component in one.
 */
public interface ISudokuExtractor {
    /**
     * @param url absolute path to sudoku img (can be jpg or png).
     * @return sudoku sudoku creted from img.
     * @throws NotFoundSudokuException if couldn't find sudoku.
     * @throws CellsExtractionFailedException if couldn't extract cells.
     * @throws DigitExtractionFailedException if couldn't extract digit.
     */
    Sudoku extract(String url) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException;

    /**
     * @param img matrix with img.
     * @return sudoku sudoku creted from img.
     * @throws NotFoundSudokuException if couldn't find sudoku.
     * @throws CellsExtractionFailedException if couldn't extract cells.
     * @throws DigitExtractionFailedException if couldn't extract digit.
     */
    Sudoku extract(Mat img) throws NotFoundSudokuException, CellsExtractionFailedException, DigitExtractionFailedException;

}
