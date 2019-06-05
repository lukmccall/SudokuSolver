package pl.sudokusolver.recognizerlib.utility.staticmethods;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.filters.NotFilter;

import java.io.File;
import java.util.Collections;

import static org.opencv.core.CvType.CV_8SC4;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
public class UtilityTest {

    @Test
    void orderPointTest(){
        MatOfPoint2f list = new MatOfPoint2f();
        list.fromArray(
                        new Point(31,0),
                        new Point(0,0),
                        new Point(0,12),
                        new Point(0,1),
                        new Point(2,1),
                        new Point(1,0),
                        new Point(1,1),
                        new Point(2,0)
                    );
        MatOfPoint2f good = new MatOfPoint2f();
        good.fromArray(
                        new Point(0,0),
                        new Point(0,1),
                        new Point(1,0),
                        new Point(1,1),
                        new Point(2,0),
                        new Point(2,1),
                        new Point(0,12),
                        new Point(31,0)
                    );
        list = Utility.orderPoints(list);
        Assert.assertEquals(good.toList(), list.toList());
    }

    @Test
    void orderPointTest2(){
        MatOfPoint2f listToTest = new MatOfPoint2f();
        listToTest.fromArray(
                new Point(10,10),
                new Point(20,25),
                new Point(0,10),
                new Point(5,6),
                new Point(11,12),
                new Point(20,20)

        );
        MatOfPoint2f good = new MatOfPoint2f();
        good.fromArray(
                new Point(0,10),
                new Point(5,6),
                new Point(10,10),
                new Point(11,12),
                new Point(20,20),
                new Point(20,25)
        );
        listToTest = Utility.orderPoints(listToTest);
        Assert.assertEquals(good.toList(), listToTest.toList());
    }

    @Test
    void distanceTest1(){
        MatOfPoint2f v = new MatOfPoint2f();

        v.fromArray(new Point(0, 0), new Point(5,5));
        Assert.assertEquals(7, Utility.distance(v));

        v.fromArray(new Point(0, 0), new Point(0,0));
        Assert.assertEquals(0, Utility.distance(v));

        v.fromArray(new Point(3, 1), new Point(15,54));
        Assert.assertEquals(54, Utility.distance(v));
    }

    @Test
    void distanceTest2(){
        MatOfPoint2f toTest = new MatOfPoint2f();

        toTest.fromArray(new Point(0, 0), new Point(1,1));
        Assert.assertEquals(1, Utility.distance(toTest));

        toTest.fromArray(new Point(1, 0), new Point(0,1));
        Assert.assertEquals(1, Utility.distance(toTest));

        toTest.fromArray(new Point(2, 0), new Point(0,2));
        Assert.assertEquals(2, Utility.distance(toTest));

        toTest.fromArray(new Point(5, 7), new Point(7,5));
        Assert.assertEquals(2, Utility.distance(toTest));

        toTest.fromArray(new Point(7, 5), new Point(5,7));
        Assert.assertEquals(2, Utility.distance(toTest));

        toTest.fromArray(new Point(49, 49), new Point(0,0));
        Assert.assertEquals(69, Utility.distance(toTest));

    }


    @Test
    void applyFiltersTest(){
        Mat mat = Mat.ones(5,5,CV_8UC1);
        Core.multiply(mat, new Scalar(255), mat);
        Mat cp = mat.clone();
        Utility.applyFilters(mat, null);

        Assert.assertEquals(cp.dump(), mat.dump());

        Utility.applyFilters(mat, Collections.singletonList(new NotFilter()));
        Assert.assertEquals(Mat.zeros(5,5,CV_8UC1).dump(), mat.dump());
    }

    @Test
    void matToBufferedImageTest(){
    }

    
    @Test
    void getSVM(){
        File file = new File(Utility.getSVMDump());
        Assert.assertTrue("SVM dump should be in resources", file.exists());
    }

}