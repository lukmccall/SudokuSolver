package pl.sudokusolver.recognizerlib.utility.comparators;

import org.opencv.core.Point;

import java.util.Comparator;

/**
 * Comparator witch compare distance on Y axis, then it looking for distance on X axis.<br>
 * If two points on Y axis have distance <= 10 they are treated as same point.
 * */
public class PointComparator implements Comparator<Point> {
    /**
     * @param y1 first point
     * @param y2 second point
     * @return y1 compare to y2, but if distance between this two points is <= 10 return 0.
     */
    private int compareY(double y1, double y2){
        if(Math.abs(y1-y2) < 10) return 0;
        return Double.compare(y1,y2);
    }
    @Override
    public int compare(Point o1, Point o2) {
       if(o1.equals(o2)) return 0;
       if(compareY(o1.y,o2.y) == 0) return Double.compare(o1.x, o2.x);
       return compareY(o1.y, o2.y);
    }
}
