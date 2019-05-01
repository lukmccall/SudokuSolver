package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

public class KNN extends MLWrapper {
    private KNearest knn;

    public KNN(IData data){
        knn = KNearest.create();
        knn.train(data.getData(), data.getSampleType(), data.getLabels());
        sampleSize = data.getSize();
    }

    public int detect(Mat img) {
        Mat wraped = applyFilter(img);
        Mat result = new Mat();

        knn.findNearest(ImageProcessing.procSimple(wraped, sampleSize), 3, result);
        return (int) result.get(0,0)[0];
    }
}
