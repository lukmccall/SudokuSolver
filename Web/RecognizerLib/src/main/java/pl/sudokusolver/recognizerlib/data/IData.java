package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.Mat;

public interface IData {
    Mat getData();
    Mat getLabels();
    int getSampleType();
    short getSize();
    DataType getDataType();
}
