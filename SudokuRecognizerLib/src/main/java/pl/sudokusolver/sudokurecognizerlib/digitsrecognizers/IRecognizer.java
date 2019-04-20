package pl.sudokusolver.sudokurecognizerlib.digitsrecognizers;

import org.opencv.core.Mat;

public interface IRecognizer {
    short detect(Mat img);
}
