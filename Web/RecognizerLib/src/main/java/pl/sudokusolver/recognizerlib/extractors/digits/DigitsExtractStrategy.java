package pl.sudokusolver.recognizerlib.extractors.digits;

import org.opencv.core.Mat;

import java.util.Optional;

public interface DigitsExtractStrategy {
    Optional<Mat> extractDigit(Mat cell);


}
