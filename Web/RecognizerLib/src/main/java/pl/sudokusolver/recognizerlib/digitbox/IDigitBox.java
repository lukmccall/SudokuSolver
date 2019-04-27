package pl.sudokusolver.recognizerlib.digitbox;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.Optional;

public interface IDigitBox {
    Optional<Rect> getDigitBox(Mat input);
}
