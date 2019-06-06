package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.opencv.core.CvType.*;

@ExtendWith({_INIT_.class})
class ResizeFilterTest {
    @Test
    void simpleResize(){
        Mat matrix = Mat.zeros(50,50, CV_8UC1);
        new ResizeFilter(new Size(25,25)).apply(matrix);
        Assert.assertEquals(new Size(25,25),matrix.size());
    }
    @Test
    void simpleResize1(){
        Mat matrix = Mat.zeros(1000,1000, CV_8UC4);
        new ResizeFilter(new Size(15,25)).apply(matrix);
        Assert.assertEquals(new Size(15,25),matrix.size());
    }
    @Test
    void throwExceptionTest(){
        Mat matrix = Mat.zeros(25,25, CV_8U);
        assertThrows(IllegalArgumentException.class, ()->{
            new ResizeFilter(new Size(0,0)).apply(matrix);
        }, "Size must be > 0");
    }

    @Test
    void invalidCreations(){
        assertThrows(IllegalArgumentException.class, ()->{
            new ResizeFilter(new Size(-10, -20));
            new ResizeFilter(-5);
            new ResizeFilter(0);
        }, "Size must be greater than 0");
    }

}