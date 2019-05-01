package pl.sudokusolver.recognizerlib.gridextractors;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

public interface GridExtractStrategy {
    Mat matToSudokuGrid(Mat img) throws NotFoundSudokuExceptions;
}
