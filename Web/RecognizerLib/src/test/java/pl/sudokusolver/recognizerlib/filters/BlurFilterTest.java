package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_64FC1;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class BlurFilterTest {

    Mat matrix;

    @BeforeEach
    void initMatrix(){
        matrix = new Mat(3,3, CV_8UC1);
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                matrix.put(i,j, i * 10 + j);
    }

    @Test
    void blurChangeParametersTest(){
        BlurFilter blurFilter = new BlurFilter(3,11,2);
        blurFilter.apply(matrix);

        Assert.assertEquals(255.0, matrix.get(0,0)[0], 1e-15);
        Assert.assertEquals(255.0, matrix.get(0,1)[0], 1e-15);
        Assert.assertEquals(255.0, matrix.get(0,2)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,0)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,1)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,2)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,0)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,1)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,2)[0], 1e-15);
    }

    @Test
    void blurDefParametersTest(){
        BlurFilter blurFilter = new BlurFilter();
        blurFilter.apply(matrix);
        Assert.assertEquals("Expected matrix only with zeros",matrix.dump(), Mat.zeros(3,3, CV_8UC1).dump());
    }

    @Test
    void wrongTypeOfMatrixTest(){
        assertThrows(CvException.class, () -> {
            new BlurFilter().apply(Mat.zeros(3,3, CV_64FC1));
        }, "Wrong type of matrix - only support CV_8U");
    }

    @Test
    void wrongCreationTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            new BlurFilter(-1,-1,-1);
        }, "Expected invalid argument exception for BlurFilter(-1, -1, -1) - arguments below 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new BlurFilter(9,1,3);
        }, "Expected invalid argument exception for BlurFilter(9, 1, 3) - blockSize must be greater then 1");


        assertThrows(IllegalArgumentException.class, () -> {
            new BlurFilter(8,9,-1);
        }, "Expected invalid argument exception for BlurFilter(8, 9, -1) - c must be greater than 1");

        assertThrows(IllegalArgumentException.class, () -> {
                    new BlurFilter(0,9,3);
        }, "Expected invalid argument exception for BlurFilter(0,9,3) - size must be greater than 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new BlurFilter(8,9,3);
        }, "Expected invalid argument exception for BlurFilter(8, 9, 3) - size must be odd");
    }

}