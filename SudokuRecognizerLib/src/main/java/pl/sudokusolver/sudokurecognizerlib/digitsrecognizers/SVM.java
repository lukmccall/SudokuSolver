package pl.sudokusolver.sudokurecognizerlib.digitsrecognizers;

import org.opencv.core.Mat;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.IData;
import pl.sudokusolver.sudokurecognizerlib.imageprocessing.ImageProcessing;

public class SVM implements ILoader, IRecognizer{
    private org.opencv.ml.SVM svm;
    private short sampleSize;

    public SVM(IData data){
        svm = org.opencv.ml.SVM.create();
        sampleSize = data.getSize();
        svm.trainAuto(data.getData(),data.getType(), data.getLabels(), 3);
    }

    public SVM(String url){
        svm = org.opencv.ml.SVM.create();
        load(url);
    }

    public void load(String url) {
        // todo: add sample size
        svm.load(url);
    }

    public void dump(String url) {
        svm.save(url);
    }

    public short detect(Mat img) {
        Mat wraped = ImageProcessing.deskew(ImageProcessing.center(img.clone(),sampleSize),sampleSize);
        Mat result = new Mat();

        svm.predict(ImageProcessing.procSimple(wraped,sampleSize), result);
        return (short) result.get(0,0)[0];
    }
}
