package pl.sudokusolver.recognizerlib.ocr.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.ANN_MLP;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.ocr.ml.config.annConfig;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import static java.lang.Math.sqrt;
import static org.opencv.core.CvType.CV_32FC1;

/**
 * Ocr korzystający z <a href="https://en.wikipedia.org/wiki/Artificial_neural_network">Artificial neural network</a>
 */


public class ANN extends MLWrapper implements ILoader{
    private ANN_MLP ann;

    public ANN() {}

    public ANN(IData data){
        ann = ANN_MLP.create();
        sampleSize = data.getSize();

        Mat layers = new Mat(1 , 4 , CV_32FC1);
        layers.put(0, 0, sampleSize*sampleSize);
        layers.put(0, 1, 50);
        layers.put(0, 2, 50);
        layers.put(0, 3, 9);
        ann.setLayerSizes(layers);
        ann.setTermCriteria(new TermCriteria(TermCriteria.COUNT + TermCriteria.EPS, 5000, 1e-5));
        ann.setActivationFunction(ANN_MLP.SIGMOID_SYM);

        ann.train(data.getData(), data.getSampleType(), data.getLabels());
    }

    public ANN(IData data, annConfig f){
        ann = ANN_MLP.create();
        sampleSize = data.getSize();
        f.config(ann);
        ann.train(data.getData(), data.getSampleType(), data.getLabels());
    }

    public ANN(String url){
        load(url);
        sampleSize = (short) sqrt(ann.getLayerSizes().get(0,0)[0]);
    }

    @Override
    public void load(String url) {
        ann = ANN_MLP.load(url);
    }

    @Override
    public void dump(String url) {
        ann.save(url);
    }

    @Override
    public Pair<Integer,Double> recognize(Mat img) {
        Mat wraped = applyDigitFilter(img);

        Mat result = new Mat();
        ann.predict(ImageProcessing.procSimple(wraped, sampleSize), result);
        int pre = 0;
        for(int i = 0; i < 9; i++)
            if(result.get(0,pre)[0] < result.get(0,i)[0])
                pre = i + 1;
        // todo: repair this :\
        // todo: bug report
        return new Pair<>(pre, result.get(0,pre)[0]);
    }

    public ANN_MLP getML(){
        return ann;
    }
}
