package pl.sudokusolver.recognizerlib.ml;

import org.opencv.core.Mat;

public interface IRecognizer {
    short detect(Mat img);
}
