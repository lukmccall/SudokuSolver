package pl.sudokusolver.recognizerlib.extractors.digits;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxContoures;

import java.util.Optional;

public class ByteSumDigitExtractStrategy extends DigitBoxContoures implements DigitsExtractStrategy {
    @Override
    public Optional<Mat> extractDigit(Mat cell) {

        Optional<Rect> rect = getDigitBox(cell);
        if(rect.isPresent()){
            return Optional.of(new Mat(cell, rect.get()));
        }
        return Optional.empty();
    }
}
