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

    public SVM(){}

    public SVM(String url){
        load(url);
        sampleSize = (short) Math.sqrt((double) svm.getVarCount());
    }


    public SVM(IData data){
        svm = org.opencv.ml.SVM.create();
        sampleSize = data.getSize();

        svm.setType(org.opencv.ml.SVM.C_SVC);
        svm.setKernel(org.opencv.ml.SVM.POLY);
        svm.setC(2.0);
        svm.setGamma(0.5f);
        svm.setDegree(5);

        svm.train(data.getData(),data.getSampleType(), data.getLabels());
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

    public org.opencv.ml.SVM getML(){
        return svm;
    }
}
