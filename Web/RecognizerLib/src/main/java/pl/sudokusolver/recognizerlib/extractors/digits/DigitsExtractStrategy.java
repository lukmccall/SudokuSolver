package pl.sudokusolver.recognizerlib.extractors.digits;

import org.opencv.core.Mat;

import java.util.Optional;

/**
 * Abstract interpretation of digit extraction algorithm.
 */
public interface DigitsExtractStrategy {
    /**
     * Looking for digit in given image.
     * @param cell matrix with sudoku's cell. This matrix be type of <code>CV_8U</code>.
     * @return matrix with digit when algorithm correctly extracted digit, if not it return <code>Optional.Empty()</code>.
     */
    Optional<Mat> extract(Mat cell);
}
