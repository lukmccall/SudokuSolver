package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.*;
import org.opencv.photo.Photo;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.filters.ToGrayFilter;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Implementation of gird extraction algorithm.<br>
 *     Algorithm:
 *     <ul>
 *         <li>Creating working copy of input image.</li>
 *         <li>Apply denoising and adaptiveThreshold to working copy.</li>
 *         <li>Using morphological transformation on working copy.</li>
 *         <li>Getting all contours on copy.</li>
 *         <li>Creating convex hull base on biggest contour</li>
 *         <li>Approximates a polygonal curve of hull.</li>
 *         <li>Apply perspective wrap to input base on early calculated polygonal.</li>
 *     </ul>
 *
 */
public class DefaultGridExtractStrategy implements GridExtractStrategy {

    /**
     * Block size of adaptiveThreshold.<br>
     * For more information you can check <a href="https://docs.opencv.org/4.0.1/d7/d1b/group__imgproc__misc.html#ga72b913f352e4a1b1b397736707afcde3" target="_blank">opencv</a>.
     */
    private int blockSize;

    /**
     * Const c for adaptiveThreshold.<br>
     * For more information you can check <a href="https://docs.opencv.org/4.0.1/d7/d1b/group__imgproc__misc.html#ga72b913f352e4a1b1b397736707afcde3" target="_blank">opencv</a>.
     */
    private int c;

    /**
     * Set blockSize to 21, c to 2.
     */
    public DefaultGridExtractStrategy(){
        blockSize = 21;
        c = 2;
    }

    /**
     * Create object base on given arguments.
     * @param blockSize blockSize for adaptiveThreshold.
     * @param c const c for adaptiveThreshold.
     */
    public DefaultGridExtractStrategy(int blockSize, int c) {
        this.blockSize = blockSize;
        this.c = c;
    }

    @Override
    public Mat extract(Mat img) throws NotFoundSudokuException {
        // denoising and adaptiveThreshold
        Mat sudokuGridFinder = preCutProcessing(img);

        // getting contours
        List<MatOfPoint> ret = getContours(sudokuGridFinder,RETR_EXTERNAL,CHAIN_APPROX_NONE);
        int max = getBiggestBlobIndex(ret); // larges blob

        // calc hull and save it into list of points
        MatOfInt hull = new MatOfInt();
        convexHull(ret.get(max), hull);
        Point[] contourArray = ret.get(max).toArray();
        List<MatOfPoint> hullList = new ArrayList<>();
        Point[] hullPoints = new Point[hull.rows()];
        List<Integer> hullContourIdxList = hull.toList();
        for (int i = 0; i < hullContourIdxList.size(); i++) {
            hullPoints[i] = contourArray[hullContourIdxList.get(i)];
        }
        hullList.add(new MatOfPoint(hullPoints));


        // getting polygonal of hull
        Pair<MatOfPoint, MatOfPoint2f> approx = calcApprox(hullList.get(0));

        // wrap it
        Mat pW = perspectiveWrap(img, approx);

        // magic fix - resize picture by one pixel from every side
        int boardRectWitdth = 1;
        Point p1 = new Point(boardRectWitdth,boardRectWitdth);
        Point p2 = new Point(pW.width()-boardRectWitdth, pW.height()-boardRectWitdth);
        Mat output = new Mat(pW, new Rect(p1,p2));

        // cleaning
        sudokuGridFinder.release();
        pW.release();
        return output;
    }


    /**
     * @param img input matrix.
     * @return copy of input after applying denoising, adaptiveThreshold and morphological transformation.
     */
    private Mat preCutProcessing(Mat img){
        Mat sudokuGridFinder = img.clone();
        new ToGrayFilter().apply(sudokuGridFinder);
        Mat sudokuGridFinder2 = sudokuGridFinder.clone();

        Photo.fastNlMeansDenoising(sudokuGridFinder,sudokuGridFinder,100,5,5);
        adaptiveThreshold(sudokuGridFinder, sudokuGridFinder, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, this.blockSize, this.c);


        double erosion_size = 2f;
        Mat element = getStructuringElement( MORPH_RECT,
                new Size( 2*erosion_size + 1, 2*erosion_size+1 ),
                new Point( erosion_size, erosion_size ) );

        // cleaning
        element.release();
        sudokuGridFinder2.release();

        return sudokuGridFinder;
    }

    /**
     * @param contours list of all contours.
     * @return index of the biggest contours.
     */
    private int getBiggestBlobIndex(List<MatOfPoint> contours){
        double area;
        double maxArea = 0;
        int p = -1;
        for (int i = 0; i < contours.size(); i++) {
            area = contourArea(contours.get(i), false);
            if (area > 50 && area > maxArea) { // only accept contours which area are bigger than 50
                maxArea = area;
                p = i;
            }
        }
        return p;
    }

    /**
     * For more information check <a href="https://docs.opencv.org/4.0.1/d3/dc0/group__imgproc__shape.html#gadf1ad6a0b82947fa1fe3c3d497f260e0" target="_blank">openCV</a>.
     * @param img input matrix.
     * @param mode mode of findContours method.
     * @param method method of findContours.
     * @return list of all contours.
     */
    private List<MatOfPoint> getContours(Mat img, int mode,int method){
        List<MatOfPoint> ret = new ArrayList<>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, mode, method);
        heirarchy.release();
        return ret;
    }


    /**
     * @param contours matrix contains all point which creating hull.
     * @return approximate polygonal curve..
     * @throws NotFoundSudokuException if couldn't calc good approximation.
     */
    private Pair<MatOfPoint, MatOfPoint2f> calcApprox(MatOfPoint contours) throws NotFoundSudokuException {
        MatOfPoint poly = new MatOfPoint(contours);
        MatOfPoint2f dst = new MatOfPoint2f();
        MatOfPoint2f src = new MatOfPoint2f();
        poly.convertTo(src, CvType.CV_32FC2);


        final int maxIteration = 400;
        final int corners = 4;

        // default value
        double arcLength = arcLength(src, true);
        approxPolyDP(src, dst, 0.02 * arcLength, true);
        if(dst.rows() == corners) {
            src.release();
            return new Pair<>(poly, dst);
        }

        // searching for new value with different parameters
        double left = 0.0;
        double right = 1.0;

        // Looking for new parameters working similar to bin search method
        for (int i = 0; i < maxIteration; i++){

            double k = (right+left)/2.0; // middle point
            double eps = k * arcLength;

            // new approximation
            approxPolyDP(src, dst, eps * arcLength, true);

            // it is good?
            if (dst.rows() == corners) {
                src.release();
                return new Pair<>(poly, dst);
            }
            else if(dst.rows() > corners) left = k; // changing interval
            else right = k;

        }
        throw new NotFoundSudokuException();
    }

    /**
     * It's working similar to <a href="https://docs.opencv.org/3.1.0/da/d6e/tutorial_py_geometric_transformations.html" target="_blank">example</a>.
     * @param sudoku input img.
     * @param approx polygonal curve.
     * @return input img transform by given curve.
     */
    private Mat perspectiveWrap(Mat sudoku, Pair<MatOfPoint, MatOfPoint2f> approx){
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

        // cleaning
        d.release();
        poly.release();
        dst.release();
        cutted.release();
        return undistorted;
    }
}
