package pl.sudokusolver.recognizerlib.utility.comparators;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;

import static org.junit.jupiter.api.Assertions.*;

class CenterLinesComparatorTest {

    @Test
    void compareTest() {
        CenterLinesComparator clc = new CenterLinesComparator();

        Assert.assertEquals(-1, clc.compare(new Point(10, 20), new Point(10, 20)));
        Assert.assertEquals(-1, clc.compare(new Point(10, 20), new Point(12, 20)));
        Assert.assertEquals(-1, clc.compare(new Point(15, 20), new Point(12, 20)));

        Assert.assertEquals(0, clc.compare(new Point(10, 20), new Point(12, 10)));
        Assert.assertEquals(0, clc.compare(new Point(10, 20), new Point(12, 10)));
        Assert.assertEquals(0, clc.compare(new Point(10, 10), new Point(99, 10)));

        Assert.assertEquals(1, clc.compare(new Point(87, 412), new Point(99, -11)));
        Assert.assertEquals(1, clc.compare(new Point(10, 312), new Point(99, 4)));
        Assert.assertEquals(1, clc.compare(new Point(2, 10), new Point(99, 1)));
    }
}