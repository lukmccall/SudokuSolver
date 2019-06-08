package pl.sudokusolver.recognizerlib.extractors.grid;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.utility.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class DefaultGridExtractStrategyPrivateTest {

    @Test
    void getBiggestBlob() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DefaultGridExtractStrategy defaultGridExtractStrategy = new DefaultGridExtractStrategy();
        Method blob = DefaultGridExtractStrategy.class.getDeclaredMethod("getBiggestBlobIndex", List.class);
        blob.setAccessible(true);

        MatOfPoint c1 = new MatOfPoint();
        MatOfPoint c2 = new MatOfPoint();
        MatOfPoint c3 = new MatOfPoint();
        MatOfPoint c4 = new MatOfPoint();
        c1.fromArray(new Point(0, 0), new Point(0,50), new Point(50,0), new Point(50,50));
        c2.fromArray(new Point(0, -100), new Point(100,100), new Point(100,0), new Point(100,50));
        c3.fromArray(new Point(0, -40), new Point(80,40), new Point(-50,0), new Point(40,50));
        c4.fromArray(new Point(0, -1), new Point(2,2), new Point(100,0), new Point(2,0));


        LinkedList contours = new LinkedList<MatOfPoint>(){{
            add(c1);
            add(c2);
            add(c3);
            add(c4);
        }};

        Assert.assertEquals("Need to chose bigger blob", 1, blob.invoke(defaultGridExtractStrategy, contours) );

        c1.fromArray(new Point(0,0), new Point(2,2));
        c2.fromArray(new Point(-5,5), new Point(0,5), new Point(5,-5));
        contours = new LinkedList<MatOfPoint>(){{
            add(c1);
            add(c2);
        }};

        Assert.assertEquals("Need to chose bigger blob", -1, blob.invoke(defaultGridExtractStrategy, contours) );

    }

    @Test
    void calcApprox() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DefaultGridExtractStrategy defaultGridExtractStrategy = new DefaultGridExtractStrategy();
        Method calc = DefaultGridExtractStrategy.class.getDeclaredMethod("calcApprox", MatOfPoint.class);
        calc.setAccessible(true);

        MatOfPoint c = new MatOfPoint();
        c.fromArray(new Point(0,0), new Point(200,0), new Point(220, 198), new Point(-10, 180));

        Pair p = (Pair) calc.invoke(defaultGridExtractStrategy, c);

        MatOfPoint src = (MatOfPoint) p.getFirst();
        MatOfPoint2f dst = (MatOfPoint2f) p.getSecond();

        Assert.assertEquals(c.dump(), src.dump());
        Assert.assertEquals(c.dump(), dst.dump());

        c.fromArray(new Point(0,0), new Point(200,0), new Point(220, 198), new Point(-10, 180),
                    new Point(1,-2), new Point(210,5), new Point(-8, 189), new Point(11, 0), new Point(230,240));

        boolean thr = false;
        try {
            calc.invoke(defaultGridExtractStrategy, c);
        } catch (Exception e){
            thr = true;
        }
        Assert.assertTrue("Wrong data should throw",thr);
    }

    @Test
    void perspectiveWrap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DefaultGridExtractStrategy defaultGridExtractStrategy = new DefaultGridExtractStrategy();
        Method wrap = DefaultGridExtractStrategy.class.getDeclaredMethod("perspectiveWrap", Mat.class, Pair.class);
        wrap.setAccessible(true);

        Mat sudoku = Mat.ones(new Size(50,50), CV_8UC1);
        Core.multiply(sudoku, new Scalar(255), sudoku);

        Pair<MatOfPoint, MatOfPoint2f> approx = new Pair<>(new MatOfPoint(), new MatOfPoint2f());

        Point points[] = new Point[4];
        points[0] = new Point(15, 15);
        points[1] = new Point(30, 15);
        points[2] = new Point(15, 30);
        points[3] = new Point(30, 30);

        approx.getFirst().fromArray(points);
        approx.getSecond().fromArray(points);

        Mat output = (Mat) wrap.invoke(defaultGridExtractStrategy, sudoku, approx);

        // this is valid output for this data sample
        String s= "[  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0, 255, 255, 255, 255, 255, 255,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0, 255, 255, 255, 255,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0, 255, 255,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0, 255, 255,   0,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0, 255, 255, 255, 255,   0,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0, 255, 255, 255, 255, 255, 255,   0,   0,   0,   0;\n" +
                "   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0]";
        Assert.assertEquals(s, output.dump());


        sudoku = Mat.ones(new Size(10,10), CV_8UC1);
        Core.multiply(sudoku, new Scalar(255), sudoku);

        boolean thr = false;
        try {
            wrap.invoke(defaultGridExtractStrategy, sudoku, approx);
        } catch (Exception e){
            thr = true;
        }
        Assert.assertTrue("when approxy is invalid for given matrix should throw", thr);
    }
}