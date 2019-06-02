package pl.sudokusolver.recognizerlib.utility.staticmethods;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@ExtendWith({_INIT_.class})
class ImageProcessingTest {

    @Test
    void procSimple() {
        int size = 30;
        Mat mat = new Mat(size, size, CV_32FC1);

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                mat.put(i, j, 255.0);

        Mat newMat = ImageProcessing.procSimple(mat, (short) size);

        Assert.assertEquals("Expected flat matrix", 1, newMat.rows());
        Assert.assertEquals("Expected flat matrix", size * size, newMat.cols());

        for(int i = 0; i < size * size; i++)
            Assert.assertEquals(1.0, newMat.get(0,i)[0], 1e-10);
    }
    
    @Test
    void applyMask() {
        short size = 30;
        Mat mat =  Mat.ones(size, size, CV_8UC1);
        Core.multiply(mat, new Scalar(250), mat);

        MatOfPoint mask = new MatOfPoint();
        mask.fromArray(new Point(0,0), new Point(0,30),
                          new Point(30 ,0), new Point(30 ,30));

        mat = ImageProcessing.applyMask(mat, mask);
        int c = 0;
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                if(mat.get(i ,j)[0] == 250.0) c++;

        Assert.assertEquals(242, c);
    }

//    Została przestestowana w MaxResizeFilter
//    @Test
//    void resizeToMaxSize() {
//    }
}