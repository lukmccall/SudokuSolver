package pl.sudokusolver.recognizerlib.utility.comparators;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {
    private int compareY(double y1, double y2){
        if(Math.abs(y1-y2) < 10) return 0;
        return Double.compare(y1,y2);
    }
    @Override
    public int compare(Point o1, Point o2) {
       if(o1.equals(o2)) return 0;
       if(compareY(o1.y,o2.y) == 0) return (Double.compare(o1.x, o2.x));
       return compareY(o1.y, o2.y);
    }
}
