package pl.sudokusolver.recognizerlib.utility.comparators;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;

import static org.junit.jupiter.api.Assertions.*;

class PointComparatorTest {

    @Test
    void compareTestOnlyY(){
        PointComparator pc = new PointComparator();
        Assert.assertEquals(0, pc.compare(new Point(0,0), new Point(0,0)));
        Assert.assertEquals(0, pc.compare(new Point(0,-9), new Point(0,0)));
        Assert.assertEquals(0, pc.compare(new Point(0,9), new Point(0,0)));
        Assert.assertEquals(0, pc.compare(new Point(0,0), new Point(0,-9)));

        Assert.assertEquals(1, pc.compare(new Point(0,10), new Point(0,0)));
        Assert.assertEquals(1, pc.compare(new Point(0,0), new Point(0,-10)));
        Assert.assertEquals(1, pc.compare(new Point(0,-5), new Point(0,-25)));
        Assert.assertEquals(1, pc.compare(new Point(0,9999), new Point(0,-1)));
        Assert.assertEquals(1, pc.compare(new Point(0,19), new Point(0,9)));

        Assert.assertEquals(-1, pc.compare(new Point(0,-10), new Point(0,0)));
        Assert.assertEquals(-1, pc.compare(new Point(0,-20), new Point(0,-10)));
        Assert.assertEquals(-1, pc.compare(new Point(0,-35), new Point(0,-25)));
        Assert.assertEquals(-1, pc.compare(new Point(0,-21), new Point(0,-1)));
        Assert.assertEquals(-1, pc.compare(new Point(0,0), new Point(0,10)));
    }

    @Test
    void compareTestOnlyX() {

        PointComparator pc = new PointComparator();

        Assert.assertEquals(0, pc.compare(new Point(0,0), new Point(0,0)));
        Assert.assertEquals(0, pc.compare(new Point(999,0), new Point(999,0)));
        Assert.assertEquals(0, pc.compare(new Point(-999,0), new Point(-999,0)));
        Assert.assertEquals(0, pc.compare(new Point(123456,0), new Point(123456,0)));

        Assert.assertEquals(1, pc.compare(new Point(1000,0), new Point(-1000,0)));
        Assert.assertEquals(1, pc.compare(new Point(999,0), new Point(998,0)));
        Assert.assertEquals(1, pc.compare(new Point(35600,0), new Point(-35600,0)));
        Assert.assertEquals(1, pc.compare(new Point(1,0), new Point(0,0)));

        Assert.assertEquals(-1, pc.compare(new Point(-10,0), new Point(0,0)));
        Assert.assertEquals(-1, pc.compare(new Point(499,0), new Point(500,0)));
        Assert.assertEquals(-1, pc.compare(new Point(20,0), new Point(999999,0)));
        Assert.assertEquals(-1, pc.compare(new Point(5,0), new Point(123,0)));


    }


    @Test
    void compareTest() {
        PointComparator pc = new PointComparator();

        Assert.assertEquals(0, pc.compare(new Point(100,200), new Point(100,200)));
        Assert.assertEquals(0, pc.compare(new Point(100,209), new Point(100,200)));
        Assert.assertEquals(0, pc.compare(new Point(100,191), new Point(100,200)));

        Assert.assertEquals(-1, pc.compare(new Point(100,191), new Point(142,200)));
        Assert.assertEquals(-1, pc.compare(new Point(100,200), new Point(1667,200)));
        Assert.assertEquals(-1, pc.compare(new Point(100,209), new Point(313,200)));

        Assert.assertEquals(1, pc.compare(new Point(201,220), new Point(202,210)));
        Assert.assertEquals(1, pc.compare(new Point(10312410,200), new Point(1667,200)));
        Assert.assertEquals(1, pc.compare(new Point(103210,209), new Point(313,200)));

    }
}