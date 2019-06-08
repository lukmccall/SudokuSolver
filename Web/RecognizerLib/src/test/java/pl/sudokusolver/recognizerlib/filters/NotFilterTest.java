package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class NotFilterTest {

    @Test
    void applyTest(){
        Mat matrix = Mat.zeros(3,3,CV_8UC1);
        Mat matrixExpected = Mat.ones(3,3,CV_8UC1);
        Scalar alpha = new Scalar(255);
        Core.multiply(matrixExpected, alpha, matrixExpected);
        new NotFilter().apply(matrix);
        Assert.assertEquals(matrixExpected.dump(), matrix.dump());

        new NotFilter().apply(matrix);
        Mat matrixExpected2 = Mat.zeros(3,3,CV_8UC1);
        Assert.assertEquals(matrixExpected2.dump(),matrix.dump());
    }


}