package pl.sudokusolver.recognizerlib.imageprocessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class LineDetector {
    /*
     * 'true' means row-iterator
     * 'false' mean col-iterator
     */
    private boolean aggregator;

    private static int TRIES = 6;
    private static int THRESHOLD = 2;

    private Mat m;

    private boolean found = false;
    private int nTry = 0;
    private int foundAt = 0;

    private Mat temp;

    private LineDetector(Mat m, boolean aggregator) {
        this.m = m;
        this.aggregator = aggregator;
    }

    /**
     * Creates a detector based on 'm' matrix, and iterate throw cols
     */
    public static LineDetector col(Mat m) {
        return new LineDetector(m, false);
    }

    /**
     * Creates a detector based on 'm' matrix, and iterate throw rows
     */
    public static LineDetector row(Mat m) {
        return new LineDetector(m, true);
    }

    public void step(int i) {

        if (nTry < TRIES) {
            temp = getVector(i);

            if (Core.countNonZero(temp) < THRESHOLD) {
                if (!found) {
                    foundAt = i;
                    found = true;
                }

                nTry++;
            } else if (found) {
                found = false;
            }
        }
    }

    private Mat getVector(int i) {
        return aggregator ? m.row(i) : m.col(i);
    }

    public int get() {
        return foundAt;
    }

}
