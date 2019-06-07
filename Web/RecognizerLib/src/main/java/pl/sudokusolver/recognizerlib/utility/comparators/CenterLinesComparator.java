package pl.sudokusolver.recognizerlib.utility.comparators;

import org.opencv.core.Point;

import java.util.Comparator;

/**
 * Porównuje współrzędną x pierwszego punktu z współrzędną y drugiego punktu, punkt(x,y)
 */
public class CenterLinesComparator implements Comparator<Point> {
    @Override
    public int compare(Point t1, Point t2) {
        return Integer.compare((int) t1.x, (int) t2.y);
    }
}