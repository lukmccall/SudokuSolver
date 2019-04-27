package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.utility.ImageProcessing;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class Grid {
    private Mat sudokuImg;

    public Grid(){}

    public Grid(String url){
        this.imgToSudokuGrid(url);
    }

    public Grid(Mat sudoku){
        this.matToSudokuGrid(sudoku);
    }

    private static int getBiggestBlobIndex(List<MatOfPoint> contours){
        double area;
        double maxarea = 0;
        int p = -1;
        for (int i = 0; i < contours.size(); i++) {
            area = contourArea(contours.get(i), false);
            if (area > 50) {
                if (area > maxarea) {
                    maxarea = area;
                    p = i;
                }
            }
        }
        return p;
    }

    private static List<MatOfPoint> getContours(Mat img){
        List<MatOfPoint> ret = new ArrayList<MatOfPoint>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        return ret;
    }

    private static Pair<MatOfPoint, MatOfPoint2f> calcApprox(MatOfPoint contours){
        MatOfPoint poly = new MatOfPoint(contours);
        MatOfPoint2f dst = new MatOfPoint2f();
        MatOfPoint2f src = new MatOfPoint2f();
        poly.convertTo(src, CvType.CV_32FC2);

        double arcLength = Imgproc.arcLength(src, true);
        approxPolyDP(src, dst, 0.02 * arcLength, true);

        return new Pair<>(poly, dst);
    }

    private void cleanLines(){
        Mat proccesd = new Mat();
        cvtColor(sudokuImg, proccesd, Imgproc.COLOR_RGB2GRAY);
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
        sudokuImg = proccesd;
    }

    public void matToSudokuGrid(Mat sudoku){
        Mat outerBox = ImageProcessing.applyFilters(sudoku);

        List<MatOfPoint> contours = getContours(outerBox);

        Pair<MatOfPoint, MatOfPoint2f> approx = calcApprox(contours.get(getBiggestBlobIndex(contours)));

        MatOfPoint poly = approx.getFirst();
        MatOfPoint2f dst = approx.getSecond();

        int size = Utility.distance(dst);

        Mat cutted = ImageProcessing.applyMask(sudoku, poly);
        MatOfPoint2f order = Utility.orderPoints(dst);

        Size reshape = new Size(size, size);

        Mat undistorted = new Mat(reshape, CvType.CV_8UC1);

        MatOfPoint2f d = new MatOfPoint2f();
        d.fromArray(new Point(0, 0), new Point(0, reshape.width), new Point(reshape.height, 0),
                new Point(reshape.width, reshape.height));

        warpPerspective(cutted, undistorted, getPerspectiveTransform(order, d), reshape);

        sudokuImg = undistorted;

        this.cleanLines();
    }

    public void imgToSudokuGrid(String url) {
        matToSudokuGrid(imread(url, IMREAD_UNCHANGED));
    }

    public Mat getSudokuImg() {
        return sudokuImg;
    }
}
