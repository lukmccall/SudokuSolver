package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.utility.ImageProcessing;

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

    public int detect(Mat img) {
        Mat wraped = applyFilter(img);
        Mat result = new Mat();

        svm.predict(ImageProcessing.procSimple(wraped,sampleSize), result);
        return (int) result.get(0,0)[0];
    }
}
