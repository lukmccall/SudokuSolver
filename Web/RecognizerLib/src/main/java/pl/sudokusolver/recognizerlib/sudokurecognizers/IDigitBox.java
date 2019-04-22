package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.Optional;

public interface IDigitBox {
    Optional<Rect> getDigitBox(Mat input);
}
