package pl.sudokusolver.recognizerlib.digitbox;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import pl.sudokusolver.recognizerlib._INIT_;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class DigitBoxContouresTest {

    @Test
    void getDigitBox(){
        Mat input = Mat.zeros(30,30, CV_8UC1);
        for(int i = 0; i < 30; i++)
            for (int j = 0; j < 30; j++)
                if(i >= 10 && i <= 20 && j >= 10 && j <= 20)
                    input.put(i,j, 255);

        Optional<Rect> optionalRect = new DigitBoxContoures().getDigitBox(input);

        Assert.assertTrue(optionalRect.isPresent());

        Assert.assertEquals(10, optionalRect.get().x);
        Assert.assertEquals(10, optionalRect.get().y);

        //because output rectangle need to be a little bit bigger
        Assert.assertEquals(12, optionalRect.get().width);
        Assert.assertEquals(12, optionalRect.get().height);
    }

    @Test
    void digitNotFound(){
        Mat input = Mat.zeros(30,30, CV_8UC1);
        Optional<Rect> optionalRect = new DigitBoxContoures().getDigitBox(input);
        Assert.assertFalse(optionalRect.isPresent());
    }

    @Test
    void digitCoverAllImage(){
        Mat input = Mat.zeros(30,30, CV_8UC1);
        for(int i = 0; i < 30; i++)
            for (int j = 0; j < 30; j++)
                    input.put(i,j, 255);

        Optional<Rect> optionalRect = new DigitBoxContoures().getDigitBox(input);

        Assert.assertTrue(optionalRect.isPresent());

        Assert.assertEquals(0, optionalRect.get().x);
        Assert.assertEquals(0, optionalRect.get().y);

        Assert.assertEquals(30, optionalRect.get().width);
        Assert.assertEquals(30, optionalRect.get().height);
    }
}