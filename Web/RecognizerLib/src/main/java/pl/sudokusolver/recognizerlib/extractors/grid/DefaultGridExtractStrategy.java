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
 * Abstrakcyjna reprezentacja algorytmów do extrakcji siatki sudoku.<br>
 *
 *     Algorytm ekstrakcji:
 *     <ul>
 *         <li>Nałożenie rozmycia</li>
 *         <li>Znalezienie wszytkich konturów</li>
 *         <li>Wybranie największego z nich (ze względu na pole)</li>
 *         <li>Wycięcie konturu oraz zmiana perspektywy zdjęcia</li>
 *         <li>Przekonwertowanie na czarno-białe zdjęcie</li>
 *     </ul>
 *
 */
public class DefaultGridExtractStrategy implements GridExtractStrategy {

    private int blockSize;
    private int c;

    public DefaultGridExtractStrategy(){
        blockSize = 33;
        c = 9;
    }

    public DefaultGridExtractStrategy(int blockSize, int c) {
        this.blockSize = blockSize;
        this.c = c;
    }

    //todo: update doc
    @Override
    public Mat extractGrid(Mat img) throws NotFoundSudokuException {
        Mat sudokuGridFinder = preCutProcessing(img);
        List<MatOfPoint> ret = getContours(sudokuGridFinder,RETR_EXTERNAL,CHAIN_APPROX_SIMPLE);
        int max = getBiggestBlobIndex(ret);


        drawContours(img,ret,max,new Scalar(0,0,0),3); // magical fix

        Mat outbox = img.clone();
        new ToGrayFilter().apply(outbox);

        Photo.fastNlMeansDenoising(outbox,outbox,50,5,10);

        adaptiveThreshold(outbox, outbox, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, blockSize, c);

        List<MatOfPoint> contours = getContours(outbox,RETR_EXTERNAL,CHAIN_APPROX_SIMPLE);
        Pair<MatOfPoint, MatOfPoint2f> approx = calcApprox(contours.get(getBiggestBlobIndex(contours)));

        return perspectiveWrap(img, approx);
    }

    private Mat preCutProcessing(Mat img){
        Mat sudokuGridFinder = img.clone();
        new ToGrayFilter().apply(sudokuGridFinder);
        Mat sudokuGridFinder2 = sudokuGridFinder.clone();

        Photo.fastNlMeansDenoising(sudokuGridFinder,sudokuGridFinder,50,10,10);
        Core.addWeighted(sudokuGridFinder2,1.5f,sudokuGridFinder,-0.5f,0.5f,sudokuGridFinder);

        Photo.fastNlMeansDenoising(sudokuGridFinder,sudokuGridFinder,50,10,10);

        adaptiveThreshold(sudokuGridFinder, sudokuGridFinder, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, 21, 2);

        double erosion_size = 2;
        Mat structImage = getStructuringElement(MORPH_RECT, new Size(erosion_size,erosion_size));
        morphologyEx(sudokuGridFinder,sudokuGridFinder,MORPH_OPEN, structImage);


        erosion_size = 1f;
        Mat element = getStructuringElement( MORPH_RECT,
                new Size( 2*erosion_size + 1, 2*erosion_size+1 ),
                new Point( erosion_size, erosion_size ) );


        erode( sudokuGridFinder, sudokuGridFinder, element );
        dilate( sudokuGridFinder, sudokuGridFinder, element );

        return sudokuGridFinder;
    }

    private int getBiggestBlobIndex(List<MatOfPoint> contours){
        double area;
        double maxArea = 0;
        int p = -1;
        for (int i = 0; i < contours.size(); i++) {
            area = contourArea(contours.get(i), false);
            if (area > 50 && area > maxArea) {
                maxArea = area;
                p = i;
            }
        }
        return p;
    }

    private List<MatOfPoint> getContours(Mat img, int mode,int method){
        List<MatOfPoint> ret = new ArrayList<>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, mode, method);
        return ret;
    }


    private Pair<MatOfPoint, MatOfPoint2f> calcApprox(MatOfPoint contours) throws NotFoundSudokuException {
        MatOfPoint poly = new MatOfPoint(contours);
        MatOfPoint2f dst = new MatOfPoint2f();
        MatOfPoint2f src = new MatOfPoint2f();
        poly.convertTo(src, CvType.CV_32FC2);

        final int maxIteration = 100;
        final int corners = 4;

        // default value
        double arcLength = arcLength(src, true);
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

        throw new NotFoundSudokuException();

    }

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
        return undistorted;
    }
}
