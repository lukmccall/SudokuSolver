package pl.sudokusolver.recognizerlib.ocr.ml;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static pl.sudokusolver.recognizerlib._TestUtility_.getPathToResource;

@ExtendWith({_INIT_.class})
class ANNTest {

    @Test
    void rec(){
        ANN ann = new ANN(Utility.getANNDump());
        int goodAns[] = new int[]{9,3,8,5,4};

        Assert.assertNotNull("Couldn't create ann model",ann.getMl());

        for (int i = 1; i <= 5; i++){
            Mat img = imread(getPathToResource("/rectest/"+i+".jpg"), -1);
            Assert.assertEquals(goodAns[i-1], (int)ann.recognize(img).getFirst());
        }

    }

}