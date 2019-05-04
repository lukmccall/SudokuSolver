package pl.sudokusolver.recognizerlib.utility;

import org.opencv.core.Core;
import org.opencv.core.Mat;


/**
 * <a href="https://github.com/joseluisdiaz/sudoku-solver">joseluisdiaz</a>
 * @author José Luis Diaz (diazjoseluis at gmail dot com)
 */
public class LineDetector {
    /**
     * <code>true</code> - iteracja przez wiersze<br>
     * <code>false</code> - iteracja przez kolumny
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
     * Tworzy detektor w oparciu o macierz oraz iteruje przez columny
     * @param m macierz wejściowa
     * @return detektor
     */
    public static LineDetector col(Mat m) {
        return new LineDetector(m, false);
    }

    /**
     * Tworzy detektor w oparciu o macierz oraz iteruje przez wiersze
     * @param m macierz wejściowa
     * @return detektor
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
