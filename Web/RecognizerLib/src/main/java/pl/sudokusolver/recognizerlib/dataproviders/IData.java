package pl.sudokusolver.recognizerlib.dataproviders;

import org.opencv.core.Mat;

public interface IData {
    Mat getData();
    Mat getLabels();
    int getType();
    short getSize();
}
