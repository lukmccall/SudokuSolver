package pl.sudokusolver.recognizerlib.gridextractors;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

public interface ExtractStrategy {
    Mat matToSudokuGrid(Mat img) throws NotFoundSudokuExceptions;
}
