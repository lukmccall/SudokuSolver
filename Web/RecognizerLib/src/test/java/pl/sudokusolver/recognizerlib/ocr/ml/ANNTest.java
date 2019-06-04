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

import java.io.IOException;

import static org.opencv.core.CvType.CV_32FC1;

@ExtendWith({_INIT_.class})
class ANNTest {
    @Test
    void learnAndTest() throws VersionMismatchException, IOException {
        // learn

        IData data = MNISTReader.read("..\\..\\Data\\images", "..\\..\\Data\\labels", DataType.Complex);
        IData testData = MNISTReader.read(
                _TestUtility_.getPathToResource("/mnisttest/images"),
                _TestUtility_.getPathToResource("/mnisttest/labels"), DataType.Simple
        );


        ANN ml = new ANN(data, (ann) -> {
            Mat layers = new Mat(1, 4, CV_32FC1);
            layers.put(0, 0, data.getSize() * data.getSize());
            layers.put(0, 1, 500);
            layers.put(0, 2, 150);
            layers.put(0, 3, 9);
            ann.setLayerSizes(layers);
            ann.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 500, 0.001));
            ann.setActivationFunction(ANN_MLP.SIGMOID_SYM, 0, 0);
            ann.setTrainMethod(ANN_MLP.BACKPROP, 0.0001);
//                    30 15
//                    Score: 0.9644124168514412
//                    50 25
//                    Score: 0.9717294900221729
//                    60 30
//                    Score: 0.9719512195121951
//                    80 40
//                    Score: 0.9772727272727273
//                    100 50
//                    Score: 0.9788248337028825
        });
//                ANN ml = new ANN("../../Data/ann.xml"); // 0.9780487804878049
//                ml.dump("../../Data/ann.xml");

        int good = 0;
        short sampleSize = testData.getSize();
        for (int i = 0; i < testData.getData().rows(); i++) {
            Mat img = new Mat(1, sampleSize * sampleSize, CV_32FC1);
            for (int x = 0; x < sampleSize*sampleSize; x++)
                    img.put(0, x, testData.getData().get(i, x)[0]);
            Mat result = new Mat();

//            for svm
//            double dist = ml.getML().predict(img, result,1);
//            int gest  = (int) result.get(0,0)[0];
            ((ANN_MLP)ml.getMl()).predict(img, result);
            int pre = 1;
            for (int u = 0; u < 9; u++)
                if (result.get(0, pre - 1)[0] < result.get(0, u)[0])
                    pre = u + 1;

            int gest = pre;
            int goodAns = (int) testData.getLabels().get(i, 0)[0];
            if (gest == goodAns)
                good++;
        }
        System.out.println("Score: " + ((double) good / (double) testData.getData().rows()));

    }
}