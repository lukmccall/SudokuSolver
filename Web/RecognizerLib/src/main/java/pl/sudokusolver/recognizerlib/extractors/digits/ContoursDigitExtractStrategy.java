package pl.sudokusolver.recognizerlib.extractors.digits;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Implementacja algorytmu extrakcji cyfry.
 * <p>
 *     Podobnie jak w {@link pl.sudokusolver.recognizerlib.digitbox.DigitBoxContoures}.
 *     Różnia jest w użytych warunka oraz w wyborze odpowiedniego ograniczenia
 * </p>
 */
public class ContoursDigitExtractStrategy implements DigitsExtractStrategy {
    @Override
    public Optional<Mat> extractDigit(Mat cell) {
        Mat img = cell.clone();

        List<MatOfPoint> countours = Lists.newArrayList();


        findContours(img, countours, new Mat(), RETR_TREE , CHAIN_APPROX_SIMPLE, new Point(0,0));

        Mat res;
        List<Rect> rects = new ArrayList<>();
        List<Double> araes = new ArrayList<>();

        for (MatOfPoint countour : countours) {
            Rect boundbox = boundingRect(countour);

//            System.out.println(boundbox.size());
            if (boundbox.height > 6 && boundbox.width > 2
                    && boundbox.height < (((double)cell.height())*3.8/5.0)
                    && boundbox.width < (((double)cell.width())*3.5/5.0)) {
//                System.out.println("Pass " + boundbox.size());
                double aspectRatio = ((double) boundbox.height) / ((double) boundbox.width);
                if (aspectRatio >= 1 && aspectRatio < 3 ) {
                    rects.add(boundbox);
                    double area = contourArea(countour);
                    araes.add(area);
                }
            }
        }

        if (!araes.isEmpty()) {
            Double d = Collections.max(araes);
//            System.out.println("Araes: ");
//            araes.forEach(System.out::println);
//            System.out.println("Best: " + rects.get(araes.indexOf(d)).size());
            res = new Mat(img, rects.get(araes.indexOf(d)));
//            copyMakeBorder(res, res, 10, 10, 10, 10, BORDER_CONSTANT, new Scalar(255, 255, 255, 255));
//            resize(res, res, new Size(28, 28));
            return Optional.of(res);
        } else {
            return Optional.empty();
        }
    }
}
