package pl.sudokusolver.recognizerlib.ocr;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.data.DataType;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.data.MNISTReader;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.io.IOException;

import static org.opencv.core.CvType.CV_32FC1;

@ExtendWith({_INIT_.class})
class IRowRecognizerTest {

    static IData testData;

    @BeforeAll
    static void init() throws VersionMismatchException, IOException {
        testData = MNISTReader.read(
                _TestUtility_.getPathToResource("/mnisttest/images"),
                _TestUtility_.getPathToResource("/mnisttest/labels"), DataType.Simple
        );
    }

    @Test
    @Ignore
    void ANN(){
        IRowRecognizer ann = new ANN(Utility.getANNDump());
        int good = 0;
        short sampleSize = testData.getSize();
        good = getGood(ann, good, sampleSize);
        double acc = ((double) good / (double) testData.getData().rows());
        Assert.assertTrue("ANN should have more then 90% accuracy. Have " + acc, acc > 0.9);
        System.out.println("ANN have " + acc + " accuracy");
    }

    @Test
    @Ignore
    void SVM(){
        IRowRecognizer svm = new SVM(Utility.getSVMDump());
        int good = 0;
        short sampleSize = testData.getSize();
        good = getGood(svm, good, sampleSize);
        double acc = ((double) good / (double) testData.getData().rows());
        Assert.assertTrue("SVM should have more then 90% accuracy. Have " + acc, acc > 0.9);
        System.out.println("SVM have " + acc + " accuracy");
    }

    private int getGood(IRowRecognizer model, int good, short sampleSize) {
        for (int i = 0; i < testData.getData().rows(); i++) {
            Mat img = new Mat(1, sampleSize * sampleSize, CV_32FC1);
            for (int x = 0; x < sampleSize*sampleSize; x++)
                img.put(0, x, testData.getData().get(i, x)[0]);

            int pre = model.rowRecognize(img);
            int goodAns = (int) testData.getLabels().get(i, 0)[0];
            if (pre == goodAns) good++;
        }
        return good;
    }
}