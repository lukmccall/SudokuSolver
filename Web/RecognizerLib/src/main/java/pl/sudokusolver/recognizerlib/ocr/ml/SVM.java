package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

/**
 * Ocr korzystający z <a href="https://en.wikipedia.org/wiki/Support-vector_machine">Support-vector machine</a>
 */
public class SVM extends MLWrapper implements ILoader{
    private org.opencv.ml.SVM svm;

    public SVM(IData data){
        svm = org.opencv.ml.SVM.create();
        sampleSize = data.getSize();
        svm.trainAuto(data.getData(),data.getSampleType(), data.getLabels(), 3);
    }

    public SVM(String url, short sampleSize){
        this.sampleSize = sampleSize;
        load(url);
    }

    public void load(String url) {
        svm = org.opencv.ml.SVM.load(url);
    }

    public void dump(String url) {
        svm.save(url);
    }

    public Pair<Integer, Double> recognize(Mat img) {
        Mat wraped = applyDigitFilter(img);
        Mat result = new Mat();

        double dist = svm.predict(ImageProcessing.procSimple(wraped,sampleSize), result,1);

        return new Pair<>((int) result.get(0,0)[0],dist);
    }
}
