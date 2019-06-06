package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import static org.opencv.imgproc.Imgproc.HoughLinesP;
import static org.opencv.imgproc.Imgproc.line;

/**
 * Filter służący do usuwania lini
 */
public class CleanLinesFilter implements IFilter {
    private int threshold;
    private int minLineSize;
    private int lineGap;
    private IFilter blurFilter;


    public CleanLinesFilter() {
        lineGap = 20;
        minLineSize = 200;
        threshold = 80;

        blurFilter = new BlurFilter(11,5,2);
    }

    /**
     * @param threshold parametr używany w <a href="https://docs.opencv.org/4.0.1/dd/d1a/group__imgproc__feature.html#ga8618180a5948286384e3b7ca02f6feeb">HoughLinesP</a>
     * @param minLineSize parametr używany w <a href="https://docs.opencv.org/4.0.1/dd/d1a/group__imgproc__feature.html#ga8618180a5948286384e3b7ca02f6feeb">HoughLinesP</a>
     * @param lineGap parametr używany w <a href="https://docs.opencv.org/4.0.1/dd/d1a/group__imgproc__feature.html#ga8618180a5948286384e3b7ca02f6feeb">HoughLinesP</a>
     */
    public CleanLinesFilter(int threshold, int minLineSize, int lineGap) {
        this.threshold = threshold;
        this.minLineSize = minLineSize;
        this.lineGap = lineGap;

        blurFilter = new BlurFilter(11,5,2);
    }


    public CleanLinesFilter(int threshold, int minLineSize, int lineGap, IFilter filtr) {
        this.threshold = threshold;
        this.minLineSize = minLineSize;
        this.lineGap = lineGap;
        this.blurFilter = filtr;
    }

    @Override
    public void apply(Mat input) {
        blurFilter.apply(input);
        Mat lines = new Mat();
        HoughLinesP(input, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);

        for (int x = 0; x < lines.rows(); x++) {
            double[] vec = lines.get(x, 0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            line(input, start, end, Scalar.all(0), 3);
        }
        lines.release();
    }
}
