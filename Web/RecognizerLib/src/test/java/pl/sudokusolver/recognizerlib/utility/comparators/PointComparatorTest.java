package pl.sudokusolver.recognizerlib.utility.comparators;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;

import static org.junit.jupiter.api.Assertions.*;

class PointComparatorTest {

    @Test
    void compare() {
        PointComparator pc = new PointComparator();

        Assert.assertEquals(0, pc.compare(new Point(100,200), new Point(100,200)));
        Assert.assertEquals(0, pc.compare(new Point(100,209), new Point(100,200)));
        Assert.assertEquals(0, pc.compare(new Point(100,191), new Point(100,200)));

        Assert.assertEquals(-1, pc.compare(new Point(100,191), new Point(142,200)));
        Assert.assertEquals(-1, pc.compare(new Point(100,200), new Point(1667,200)));
        Assert.assertEquals(-1, pc.compare(new Point(100,209), new Point(313,200)));

        Assert.assertEquals(1, pc.compare(new Point(46546,191), new Point(142,200)));
        Assert.assertEquals(1, pc.compare(new Point(10312410,200), new Point(1667,200)));
        Assert.assertEquals(1, pc.compare(new Point(103210,209), new Point(313,200)));
    }
}