package pl.sudokusolver.sudokurecognizerlib.recognizers;

import org.opencv.core.Mat;
import org.opencv.ml.ANN_MLP;
import org.opencv.ml.Ml;
import pl.sudokusolver.sudokurecognizerlib.dataproviders.IData;
import pl.sudokusolver.sudokurecognizerlib.imageprocessing.ImageProcessing;

import static java.lang.Math.sqrt;
import static org.opencv.core.CvType.CV_32FC1;

public class ANN implements ILoader, IRecognizer{
    private ANN_MLP ann;
    private short sampleSize;

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

        ann.train(data.getData(), data.getType(), data.getLabels());
    }

    public ANN(String url){
        ann = ANN_MLP.create();
        load(url);
        sampleSize = (short) sqrt(ann.getLayerSizes().get(0,0)[0]);
    }

    public void load(String url) {
        ann = ANN_MLP.load(url);
    }

    public void dump(String url) {
        ann.save(url);
    }

    public short detect(Mat img) {
        Mat wraped = ImageProcessing.deskew(ImageProcessing.center(img.clone(), sampleSize), sampleSize);
        Mat result = new Mat();
        ann.predict(ImageProcessing.procSimple(wraped, sampleSize), result);
        int pre = 0;
        for(int i = 1; i < 10; i++)
            if(result.get(0,pre)[0] < result.get(0,i)[0])
                pre = i;
        return (short) pre;
    }
}
