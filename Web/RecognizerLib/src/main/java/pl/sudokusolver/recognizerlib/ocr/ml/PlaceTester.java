package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.utility.Pair;

/**
 * Klasa do testów
 */
public class PlaceTester implements IRecognizer {
    @Override
    public Pair<Integer, Double> recognize(Mat img) {
        return new Pair<>(1, 1.0);
    }
}
