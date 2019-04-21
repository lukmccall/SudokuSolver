package pl.sudokusolver.recognizerlib.dataproviders;

import org.opencv.core.Mat;

public interface IData {
    //void loadFromFile(String url, short size);
    Mat getData();
    Mat getLabels();
    int getType();
    short getSize();
}
