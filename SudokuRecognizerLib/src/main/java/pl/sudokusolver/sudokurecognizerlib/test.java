package pl.sudokusolver.sudokurecognizerlib;

import pl.sudokusolver.sudokurecognizerlib.dataproviders.DataType;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.IData;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.MNISTReader;
import pl.sudokusolver.sudokurecognizerlib.digitsrecognizers.ANN;

public class test {

    static {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println("Rec Lib Test");

        System.out.println("Getting Data");
        IData data = MNISTReader.read("../Data/images", "../Data/labels", DataType.Complex);

        System.out.println("Learning");
        ANN ann = new ANN(data);
        ann.dump("ann.xml");


    }
}
