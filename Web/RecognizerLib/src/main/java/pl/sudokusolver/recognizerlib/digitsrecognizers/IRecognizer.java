package pl.sudokusolver.recognizerlib.digitsrecognizers;

import org.opencv.core.Mat;

public interface IRecognizer {
    short detect(Mat img);
}
