package pl.sudokusolver.recognizerlib.gridextractors;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.*;

public class DefaultGridExtractStrategy implements GridExtractStrategy {

    @Override
    public Mat extractGrid(Mat img) throws NotFoundSudokuExceptions {
            Mat outerBox = ImageProcessing.applyFilters(img);
            List<MatOfPoint> contours = getContours(outerBox);
            Pair<MatOfPoint, MatOfPoint2f> approx = calcApprox(contours.get(getBiggestBlobIndex(contours)));
            return perspectiveWrap(img, approx);
    }

    protected int getBiggestBlobIndex(List<MatOfPoint> contours){
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

    protected List<MatOfPoint> getContours(Mat img){
        List<MatOfPoint> ret = new ArrayList<>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        return ret;
    }

    protected Pair<MatOfPoint, MatOfPoint2f> calcApprox(MatOfPoint contours) throws NotFoundSudokuExceptions {
        MatOfPoint poly = new MatOfPoint(contours);
        MatOfPoint2f dst = new MatOfPoint2f();
        MatOfPoint2f src = new MatOfPoint2f();
        poly.convertTo(src, CvType.CV_32FC2);

        final int maxIteration = 100;
        final int corners = 4;

        // default value
        double arcLength = Imgproc.arcLength(src, true);
        approxPolyDP(src, dst, 0.02 * arcLength, true);
        if(dst.rows() == corners)
            return new Pair<>(poly, dst);

        // searching for new
        double left = 0.0;
        double right = 1.0;

        for (int i = 0; i < maxIteration; i++){

            double k = (right+left)/2.0;

            double eps = k * arcLength;

            approxPolyDP(src, dst, eps * arcLength, true);
            if (dst.rows() == corners) return new Pair<>(poly, dst);
            else if(dst.rows() > corners) left = k;
            else right = k;

        }

        throw new NotFoundSudokuExceptions();

    }

    protected Mat perspectiveWrap(Mat sudoku, Pair<MatOfPoint, MatOfPoint2f> approx){
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
        return undistorted;
    }
}
