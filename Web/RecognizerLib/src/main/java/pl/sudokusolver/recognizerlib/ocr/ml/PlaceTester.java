package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.filters.DisplayHelper;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;

public class PlaceTester implements IRecognizer {
    @Override
    public int detect(Mat img) {
        return 1;
    }
}
