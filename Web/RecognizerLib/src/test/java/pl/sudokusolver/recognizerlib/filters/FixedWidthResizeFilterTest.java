package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.*;

@ExtendWith({_INIT_.class})
class FixedWidthResizeFilterTest {

    @Test
    void applyTest() {

        Mat matrix = Mat.zeros(new Size(600,300), CV_8UC1);
        new FixedWidthResizeFilter(300).apply(matrix);
        Assert.assertEquals(new Size(300,150),matrix.size());

        matrix = Mat.zeros(new Size(100,300), CV_8UC1);
        new FixedWidthResizeFilter(500).apply(matrix);
        Assert.assertEquals(new Size(500,1500),matrix.size());

        matrix = Mat.zeros(new Size(100,100), CV_8UC1);
        new FixedWidthResizeFilter(390).apply(matrix);
        Assert.assertEquals(new Size(390,390),matrix.size());

        matrix = Mat.zeros(new Size(100,500), CV_8UC1);
        new FixedWidthResizeFilter(200).apply(matrix);
        Assert.assertEquals(new Size(200,1000),matrix.size());
    }

    @Test
    void applyCvExceptionTest(){
        Mat matrix = Mat.zeros(new Size(600,300), CV_8UC1);
        assertThrows(CvException.class, () -> {
            new FixedWidthResizeFilter(-1).apply(matrix);
        }, "The argument given for the constructor must be greater than 0");

        Mat matrix2 = Mat.zeros(new Size(600,300), CV_8UC2);
        assertThrows(CvException.class, () -> {
            new FixedWidthResizeFilter(-1).apply(matrix2);
        }, "Wrong type of matrix - only support mat with on channel");

    }

}