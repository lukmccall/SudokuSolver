package pl.sudokusolver.sudokurecognizerlib.gridrecognizers;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.List;

import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.boundingRect;

public class DigitBoxContoures implements IDigitBox {
    public Optional<Rect> getDigitBox(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();

        findContours(input.clone(), cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);

        List<Point> lp = Lists.newArrayList();

        for (MatOfPoint p : cont) {
            Rect rect = boundingRect(p);

            double aspect = rect.height / (double) rect.width;
            double area = rect.area();

            if (aspect > 0.5 && aspect < 10 && area > 20) {
                lp.add(rect.tl());
                lp.add(rect.br());
            }

        }

        if (!lp.isEmpty()) {
            MatOfPoint points = new MatOfPoint();
            points.fromList(lp);
            return Optional.of(boundingRect(points));
        }

        return Optional.absent();

    }
}
