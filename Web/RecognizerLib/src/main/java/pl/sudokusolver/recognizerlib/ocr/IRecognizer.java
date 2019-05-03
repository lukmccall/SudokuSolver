package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.utility.Pair;

public interface IRecognizer {
    Pair<Integer, Double> recognize(Mat img);
}
