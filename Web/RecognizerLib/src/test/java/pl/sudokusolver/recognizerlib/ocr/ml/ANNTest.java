package pl.sudokusolver.recognizerlib.ocr.ml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.ml.ANN_MLP;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.data.DataType;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.data.MNISTReader;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.filters.ResizeFilter;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@ExtendWith({_INIT_.class})
class ANNTest {
    @Test
    void learnAndTest() throws VersionMismatchException, IOException {
        // learn


//        IData data = MNISTReader.read("..\\..\\Data\\images","..\\..\\Data\\labels", DataType.Complex);
//        ANN ml = new ANN(data, (ann) -> {
//            Mat layers = new Mat(1 , 4 , CV_32FC1);
//            layers.put(0, 0, data.getSize()*data.getSize());
//            layers.put(0, 1, 80);
//            layers.put(0, 2, 40);
//            layers.put(0, 3, 9);
//            ann.setLayerSizes(layers);
//            ann.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 5000, 0.001));
//            ann.setActivationFunction(ANN_MLP.SIGMOID_SYM,0,0);
//            ann.setTrainMethod(ANN_MLP.BACKPROP, 0.0001);
//        });
//
//        ml.dump("../../Data/ann.xml");
        SVM ml = new SVM("../../Data/svm.xml");

        IData testData = MNISTReader.read(
                _TestUtility_.getPathToResource("/mnisttest/images"),
                _TestUtility_.getPathToResource("/mnisttest/labels"), DataType.Simple
        );
        int good = 0;
        short sampleSize = testData.getSize();
        for(int i = 0; i < testData.getData().rows(); i++){
            Mat img = new Mat(28,28, CV_8UC1);
            int curr = 0;
            for(int x = 0; x < 28; x++)
                for(int y = 0; y < 28; y++)
                    img.put(x, y, testData.getData().get(i, curr++)[0] * 255.0);

            Mat wraped = ImageProcessing.deskew(img.clone(),sampleSize);
            Mat result = new Mat();

            double dist = ml.getML().predict(ImageProcessing.procSimple(wraped,sampleSize), result,1);
            int gest  = (int) result.get(0,0)[0];


            if(gest == (int) testData.getLabels().get(i,0)[0])
                good++;
        }
        System.out.println("Score: " + ((double)good/(double)testData.getData().rows()));
    }
}