package pl.sudokusolver.recognizerlib.ml;

import org.opencv.core.Mat;
import org.opencv.ml.ANN_MLP;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.utility.ImageProcessing;

import static java.lang.Math.sqrt;
import static org.opencv.core.CvType.CV_32FC1;

public class ANN extends MLWrapper implements ILoader{
    private ANN_MLP ann;

    public ANN(IData data){
        ann = ANN_MLP.create();
        sampleSize = data.getSize();

        Mat layers = new Mat(1 , 5 , CV_32FC1);
        layers.put(0, 0, sampleSize*sampleSize);
        layers.put(0, 1, 512);
        layers.put(0, 2, 128);
        layers.put(0, 3, 64);
        layers.put(0, 4, 10);
        ann.setLayerSizes(layers);
        ann.setActivationFunction(ANN_MLP.SIGMOID_SYM);

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
    public short detect(Mat img) {
        Mat wraped = applyFilter(img);
        Mat result = new Mat();
        ann.predict(ImageProcessing.procSimple(wraped, sampleSize), result);
        int pre = 0;
        for(int i = 1; i < 10; i++)
            if(result.get(0,pre)[0] < result.get(0,i)[0])
                pre = i;
        return (short) pre;
    }
}
