package pl.sudokusolver.recognizerlib.extractors.digits;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxContoures;

import java.util.Optional;

/**
 * Implementation of digit extraction algorithm.<br>
 * <p>
 *     We used {@link pl.sudokusolver.recognizerlib.digitbox.DigitBoxContoures}
 * </p>
 */
public class FastDigitExtractStrategy extends DigitBoxContoures implements DigitsExtractStrategy {
    @Override
    public Optional<Mat> extract(Mat cell) {
        Optional<Rect> rect = getDigitBox(cell);
        if(rect.isPresent())
            return Optional.of(new Mat(cell, rect.get()));
        return Optional.empty();
    }
}
