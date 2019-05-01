package pl.sudokusolver.recognizerlib.ocr;

import org.opencv.core.Mat;

public interface IRecognizer {
    int detect(Mat img);
}
