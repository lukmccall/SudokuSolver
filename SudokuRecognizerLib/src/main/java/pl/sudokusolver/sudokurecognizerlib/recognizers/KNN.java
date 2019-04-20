package pl.sudokusolver.sudokurecognizerlib.recognizers;

import org.opencv.core.Mat;
import org.opencv.ml.KNearest;

public class KNN implements IRecognizer {
    private KNearest knn;
    public KNN(){

    }

    public void init() {

    }

    public short detect(Mat img) {
        return 0;
    }
}
