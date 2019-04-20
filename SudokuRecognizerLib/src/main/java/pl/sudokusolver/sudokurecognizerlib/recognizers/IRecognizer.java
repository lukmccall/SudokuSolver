package pl.sudokusolver.sudokurecognizerlib.recognizers;

import org.opencv.core.Mat;

public interface IRecognizer {
    void init();
    short detect(Mat img);

}
