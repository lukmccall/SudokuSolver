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
class MedianBlurTest {

    Mat matrix;

    @BeforeEach
    void initMatrix(){
        matrix = new Mat(3,3, CV_8UC1);
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                matrix.put(i,j, i * 10 + j);
    }

    @Test
    void blurChangeParameters(){
        MedianBlur blurFilter = new MedianBlur(3,11,2);
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
    void blurDefParameters(){
        MedianBlur blurFilter = new MedianBlur();
        blurFilter.apply(matrix);

        Assert.assertEquals(255.0, matrix.get(0,0)[0], 1e-15);
        Assert.assertEquals(255.0, matrix.get(0,1)[0], 1e-15);
        Assert.assertEquals(255.0, matrix.get(0,2)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,0)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,1)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(1,2)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,0)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,1)[0], 1e-15);
        Assert.assertEquals(0.0, matrix.get(2,2)[0], 1e-15); }

    @Test
    void wrongTypeOfMatrix(){
        assertThrows(CvException.class, () -> {
            new MedianBlur().apply(Mat.zeros(3,3, CV_64FC1));
        }, "Wrong type of matrix - only support CV_8U");
    }

    @Test
    void wrongCreation(){
        assertThrows(IllegalArgumentException.class, () -> {
            new MedianBlur(-1,-1,-1);
        }, "Expected invalid argument exception for BlurFilter(-1, -1, -1) - arguments below 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new MedianBlur(9,1,3);
        }, "Expected invalid argument exception for BlurFilter(9, 1, 3) - blockSize must be greater then 1");

        assertThrows(IllegalArgumentException.class, () -> {
            new MedianBlur(8,9,3);
        }, "Expected invalid argument exception for BlurFilter(8, 9, 3) - size must be odd");
    }


}