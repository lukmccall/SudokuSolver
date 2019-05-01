package pl.sudokusolver.recognizerlib.utility.comparators;

import org.opencv.core.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {

    @Override
    public int compare(Point o1, Point o2) {
       if(o1.equals(o2)) return 0;
       if(o1.y < o2.y) return -1;
       else if(o1.y == o2.y)
           return (Double.compare(o1.x, o2.x));
       return 1;
    }
}
