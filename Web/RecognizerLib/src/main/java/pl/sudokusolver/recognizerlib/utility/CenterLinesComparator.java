package pl.sudokusolver.recognizerlib.utility;

import org.opencv.core.Point;

import java.util.Comparator;

public class CenterLinesComparator implements Comparator<Point> {
    @Override
    public int compare(Point t1, Point t2) {
        return Integer.compare((int) t1.x, (int) t2.y);
    }
}