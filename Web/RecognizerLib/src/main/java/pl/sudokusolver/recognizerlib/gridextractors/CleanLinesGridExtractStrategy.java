package pl.sudokusolver.recognizerlib.gridextractors;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

import static org.opencv.imgproc.Imgproc.adaptiveThreshold;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.line;

public class CleanLinesGridExtractStrategy extends DefaultGridExtractStrategy {

    @Override
    public Mat extractGrid(Mat img) throws NotFoundSudokuExceptions {
        Mat ret = super.extractGrid(img);
        ret = cleanLines(ret);
        return ret;
    }

    private static Mat cleanLines(Mat grid){
        Mat proccesd = new Mat();
        cvtColor(grid, proccesd, Imgproc.COLOR_RGB2GRAY);

        Imgproc.GaussianBlur(proccesd, proccesd, new Size(11, 11), 0);
        adaptiveThreshold(proccesd, proccesd, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 2);

        Mat lines = new Mat();

        int threshold = 80;
        int minLineSize = 200;
        int lineGap = 20;

        Imgproc.HoughLinesP(proccesd, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);

        for (int x = 0; x < lines.rows(); x++) {
            double[] vec = lines.get(x, 0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            line(proccesd, start, end, Scalar.all(0), 3);

        }
        return proccesd;
    }
}
