package pl.sudokusolver.sudokurecognizerlib.recognizers;

import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.IData;
import pl.sudokusolver.sudokurecognizerlib.imageprocessing.ImageProcessing;

public class KNN implements IRecognizer {
    private KNearest knn;
    private short sampleSize;

    public KNN(IData data){
        knn = KNearest.create();
        knn.train(data.getData(), data.getType(), data.getLabels());
        sampleSize = data.getSize();
    }

    public short detect(Mat img) {
        Mat wraped = ImageProcessing.deskew(ImageProcessing.center(img.clone(), sampleSize), sampleSize);
        Mat result = new Mat();

        knn.findNearest(ImageProcessing.procSimple(wraped, sampleSize), 3, result);
        return (short) result.get(0,0)[0];
    }
}
