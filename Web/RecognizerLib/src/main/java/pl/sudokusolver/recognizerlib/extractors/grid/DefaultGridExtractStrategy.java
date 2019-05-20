package pl.sudokusolver.recognizerlib.extractors.grid;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.filters.BlurFilter;
import pl.sudokusolver.recognizerlib.filters.DisplayHelper;
import pl.sudokusolver.recognizerlib.filters.IFilter;
import pl.sudokusolver.recognizerlib.filters.ToGrayFilter;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;
import pl.sudokusolver.recognizerlib.utility.Pair;
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
    /**
     * Filter nakładający rozymie na zdjęcie
     */
    private IFilter blurFilter;

    /**
     * Tworzy algorytm korzystający z domyślnego rozmycia ({@link pl.sudokusolver.recognizerlib.filters.BlurFilter})
     */
    public DefaultGridExtractStrategy(){
        blurFilter = new BlurFilter();
    }

    /**
     * @param blurFilter algorytm rozmycia używany w algorytmie
     */
    public DefaultGridExtractStrategy(IFilter blurFilter) {
        this.blurFilter = blurFilter;
    }

    @Override
    public Mat extractGrid(Mat img) throws NotFoundSudokuException {
            Mat outbox = img.clone();
        new ToGrayFilter().apply(outbox);

        Photo.fastNlMeansDenoising(outbox,outbox,50,5,10);
        adaptiveThreshold(outbox, outbox, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY_INV, 33, 2);
      //  new DisplayHelper().apply(outbox);
       // Canny(outbox,outbox,50,150);
     //  new DisplayHelper().apply(outbox);
            List<MatOfPoint> contours = getContours(outbox,RETR_EXTERNAL,CHAIN_APPROX_SIMPLE);
            Pair<MatOfPoint, MatOfPoint2f> approx = calcApprox(contours.get(getBiggestBlobIndex(contours)));
            Mat m = perspectiveWrap(img, approx);
   //     new DisplayHelper().apply(m);
            new ToGrayFilter().apply(m);
           // new DisplayHelper().apply(m);
            return m;
    }

    private int getBiggestBlobIndex(List<MatOfPoint> contours){
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

    private List<MatOfPoint> getContours(Mat img, int mode,int method){
        List<MatOfPoint> ret = new ArrayList<>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, mode, method);
        return ret;
    }

    private List<MatOfPoint> getContours(Mat img){
        List<MatOfPoint> ret = new ArrayList<>();
        Mat heirarchy = new Mat();
        findContours(img, ret, heirarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
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
