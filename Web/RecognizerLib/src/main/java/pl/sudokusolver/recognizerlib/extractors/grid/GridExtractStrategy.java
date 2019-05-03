package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

public interface GridExtractStrategy {
    Mat extractGrid(Mat img) throws NotFoundSudokuException;
}
