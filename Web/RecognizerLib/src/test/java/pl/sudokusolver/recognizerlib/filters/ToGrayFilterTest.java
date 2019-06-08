package pl.sudokusolver.recognizerlib.filters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class ToGrayFilterTest {
    @Test
    void applyTest() {

        Mat matrix = new Mat(3,3,CV_32FC3);
        new ToGrayFilter().apply(matrix);
        assertEquals(1,matrix.channels());

        matrix = new Mat(3,3,CV_32FC4);
        new ToGrayFilter().apply(matrix);
        assertEquals(1,matrix.channels());


    }

    @Test
    void applyTestIfChannel1(){
        Mat matrix = new Mat(3,3, CV_8UC1);
        new ToGrayFilter().apply(matrix);
        assertEquals(1,matrix.channels());
    }

}