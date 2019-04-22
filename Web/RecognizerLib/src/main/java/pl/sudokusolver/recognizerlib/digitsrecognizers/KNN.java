package pl.sudokusolver.recognizerlib.digitsrecognizers;

import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import pl.sudokusolver.recognizerlib.dataproviders.IData;
import pl.sudokusolver.recognizerlib.imageprocessing.ImageProcessing;

public class KNN extends MLWrapper {
    private KNearest knn;

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
