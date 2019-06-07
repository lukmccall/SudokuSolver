package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;

/**
 * Common opencv ocr interface.<br>
 * Using this interface you can pass data directly to model.
 *  */
public interface IRowRecognizer {
    /**
     * @param img matrix (vector) with data.
     * @return predicted digit
     */
    int rowRecognize(Mat img);
}
