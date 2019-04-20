package pl.sudokusolver.sudokurecognizerlib.recognizers;

import org.opencv.core.Mat;

public interface IRecognizer {
    short detect(Mat img);

}
