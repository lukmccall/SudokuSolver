package pl.sudokusolver.recognizerlib.utility.comparators;

import org.opencv.core.Point;

import java.util.Comparator;

/**
 * Comparator which is looking for x from first point and y from second point, then compare them.
 */
public class CenterLinesComparator implements Comparator<Point> {
    @Override
    public int compare(Point t1, Point t2) {
        return Integer.compare((int) t1.x, (int) t2.y);
    }
}