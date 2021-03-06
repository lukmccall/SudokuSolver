package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;


import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class MaxResizeFilterTest {

    @Test
    void applyTest(){

        Mat matix = Mat.zeros(new Size(200,200), CV_8UC1);
        new MaxResizeFilter(new Size(300,300)).apply(matix);
        Assert.assertEquals(new Size(200,200),matix.size());

        matix = Mat.zeros(new Size(600,300), CV_8UC1);
        new MaxResizeFilter(new Size(300,300)).apply(matix);

        Assert.assertEquals(new Size(300,150),matix.size());

        matix = Mat.zeros(new Size(300,600), CV_8UC1);
        new MaxResizeFilter(new Size(300,300)).apply(matix);

        Assert.assertEquals(new Size(150,300),matix.size());

        matix = Mat.zeros(new Size(1000,1000), CV_8UC1);
        new MaxResizeFilter(new Size(300,150)).apply(matix);

        Assert.assertEquals(new Size(150,150),matix.size());

    }

    @Test
    void invalidCreationsTest(){
        assertThrows(IllegalArgumentException.class, ()->{
            new MaxResizeFilter(new Size(-10, -20));
        }, "Size must be greater than 0");

        assertThrows(IllegalArgumentException.class, ()->{
            new MaxResizeFilter(new Size(0, 20));

        }, "Size must be greater than 0");


    }
}