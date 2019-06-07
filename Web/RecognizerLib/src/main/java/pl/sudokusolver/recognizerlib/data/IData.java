package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.Mat;

/**
 * Abstract data representation.<br>
 * It packing data into something that can be used by orcs.
 */
public interface IData {
    /**
     * @return matrix which contain data.
     */
    Mat getData();

    /**
     * @return matrix which contain labels.
     */
    Mat getLabels();


    /**
     * @return pattern how data are stored.<br>
     * For more information you can checkout <a href="https://docs.opencv.org/4.0.1/dd/ded/group__ml.html#ga9c57a2b823008dda53d2c7f7059a8710" target="_blank">SamplesType</a>
     */
    int getSampleType();

    /**
     * @return size of single sample. Sample must be rectangular.
     */
    short getSize();

    /**
     * @return data type
     */
    DataType getDataType();
}
