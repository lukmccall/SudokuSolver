package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;

public interface IRecognizer {
    short detect(Mat img);
}
