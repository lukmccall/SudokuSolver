package pl.sudokusolver.recognizerlib.digitbox;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgproc.Imgproc.*;

public class DigitBoxContoures implements IDigitBox {
    public Optional<Rect> getDigitBox(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();

        findContours(input, cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);

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

            Rect rect = boundingRect(points);
            if(rect.x+rect.width > input.width())
                rect.x = input.width() - rect.width;
            if(rect.y+rect.height > input.height())
                rect.y = input.height() - rect.height;

            return Optional.of(rect);
        }

        return Optional.empty();

    }
}
