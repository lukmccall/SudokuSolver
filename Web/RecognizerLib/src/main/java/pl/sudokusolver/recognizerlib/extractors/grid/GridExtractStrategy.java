package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

/**
 * Abstract interpretation of sudoku's grid extraction algorithm.
 */
public interface GridExtractStrategy {
    /**
     * Algorithm looking for sudoku's grid and trying to extract it.
     * @param img matrix with contains sudoku. Matrix can be of any type.
     * @return matrix whit extracted grid. This matrix's type of <code>CV_8UC3</code>.
     * @throws NotFoundSudokuException if couldn't find grid.
     */
    Mat extract(Mat img) throws NotFoundSudokuException;
}
