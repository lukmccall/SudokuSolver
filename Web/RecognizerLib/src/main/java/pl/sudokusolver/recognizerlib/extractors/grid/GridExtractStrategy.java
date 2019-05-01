package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

public interface GridExtractStrategy {
    Mat extractGrid(Mat img) throws NotFoundSudokuExceptions;
}
