package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.ocr.IRowModel;
import pl.sudokusolver.recognizerlib.ocr.IRowRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.config.svmConfig;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

/**
 * Ocr korzystający z <a href="https://en.wikipedia.org/wiki/Support-vector_machine">Support-vector machine</a>
 */
public class SVM extends MLWrapper implements ILoader, IRowRecognizer, IRowModel {
    private org.opencv.ml.SVM svm;

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

    public SVM(IData data, svmConfig config){
        svm = org.opencv.ml.SVM.create();
        sampleSize = data.getSize();
        config.config(svm);
        svm.train(data.getData(),data.getSampleType(), data.getLabels());
    }

    @Override
    public void load(String url) {
        svm = org.opencv.ml.SVM.load(url);
    }

    @Override
    public void dump(String url) {
        svm.save(url);
    }

    @Override
    public Pair<Integer, Double> recognize(Mat img) {
        Mat wraped = applyDigitFilter(img);
        Mat result = new Mat();

        Mat preImg = ImageProcessing.procSimple(wraped, sampleSize);
        double dist = svm.predict(preImg, result,1);

        int r = (int) result.get(0,0)[0];

        wraped.release();
        preImg.release();
        result.release();

        return new Pair<>(r,dist);
    }

    @Override
    public int rowRecognize(Mat img) {
        Mat result = new Mat();
        svm.predict(img, result,1);
        return (int) result.get(0,0)[0];
    }

    @Override
    public Object getMl() {
        return svm;
    }
}
