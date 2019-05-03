package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import org.opencv.ml.KNearest;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

public class KNN extends MLWrapper {
    private KNearest knn;

    public KNN(IData data){
        knn = KNearest.create();
        knn.train(data.getData(), data.getSampleType(), data.getLabels());
        sampleSize = data.getSize();
    }

    public Pair<Integer, Double> recognize(Mat img) {
        Mat wraped = applyFilter(img);
        Mat result = new Mat();
        Mat dist = new Mat();


        //todo: return something different then dist
        knn.findNearest(ImageProcessing.procSimple(wraped, sampleSize), 3, result, null, dist);
        return new Pair<>((int) result.get(0,0)[0], dist.get(0,0)[0]);
    }
}
