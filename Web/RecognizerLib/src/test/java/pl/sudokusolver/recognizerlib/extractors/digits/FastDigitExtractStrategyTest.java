package pl.sudokusolver.recognizerlib.extractors.digits;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.digitbox.DigitBoxContoures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class FastDigitExtractStrategyTest {

    @Test
    void digitNotFound(){
        Mat input = Mat.zeros(30,30, CV_8UC1);
        Optional<Rect> optionalRect = new FastDigitExtractStrategy().getDigitBox(input);
        Assert.assertFalse(optionalRect.isPresent());
    }


    @Test
    void digitCoverAllImage(){
        Mat input = Mat.ones(30,30, CV_8UC1);
        Core.multiply(input, new Scalar(255), input);

        Optional<Rect> optionalRect = new FastDigitExtractStrategy().getDigitBox(input);

        Assert.assertTrue(optionalRect.isPresent());

        Assert.assertEquals(0, optionalRect.get().x);
        Assert.assertEquals(0, optionalRect.get().y);

        Assert.assertEquals(30, optionalRect.get().width);
        Assert.assertEquals(30, optionalRect.get().height);
    }

}