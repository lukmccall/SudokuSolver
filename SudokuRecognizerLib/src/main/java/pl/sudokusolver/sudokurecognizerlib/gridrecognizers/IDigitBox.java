package pl.sudokusolver.sudokurecognizerlib.gridrecognizers;

import com.google.common.base.Optional;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

public interface IDigitBox {
    Optional<Rect> getDigitBox(Mat input);
}
