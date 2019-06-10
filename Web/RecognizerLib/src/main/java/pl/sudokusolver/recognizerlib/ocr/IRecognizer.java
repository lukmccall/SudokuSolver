package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.utility.Pair;

/**
 * Common ocr interface.
 */
public interface IRecognizer {
    /**
     * @param img matrix with digit.
     * @return pair which contains output from model. First value is predicted digit, second value is probability (now unused).
     * @throws DigitExtractionFailedException if couldn't extracted digits
     */
    Pair<Integer, Double> recognize(Mat img) throws DigitExtractionFailedException;
}
