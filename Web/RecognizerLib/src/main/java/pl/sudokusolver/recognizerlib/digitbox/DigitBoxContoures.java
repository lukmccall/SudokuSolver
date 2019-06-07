package pl.sudokusolver.recognizerlib.digitbox;

import com.google.common.collect.Lists;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Implementation of digit finding algorithm.
 * <p>
 * We used method which you can find <a href="https://docs.opencv.org/4.0.1/javadoc/org/opencv/imgproc/Imgproc.html#findContours-org.opencv.core.Mat-java.util.List-org.opencv.core.Mat-int-int-">findContours</a>.
 * We looking for all contours, then we calc bounding rectangles of this contours.<br>
 * Then we only accept this rectangles which aspect ration is in rang of [0.5, 10] and have area bigger than 20.<br>
 * Then we create another bounding rectangle on the basis of other rectangles.
 * </p>
 */
public class DigitBoxContoures implements IDigitBox {
    public Optional<Rect> getDigitBox(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();

        // finding all contours
        findContours(input, cont, new Mat(), RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

        List<Point> lp = Lists.newArrayList();

        // filtering contours - take only if match ours parameters
        for (MatOfPoint p : cont) {
            Rect rect = boundingRect(p);

            double aspect = rect.height / (double) rect.width;
            double area = rect.area();

            if (aspect > 0.3 && aspect < 4 && area > 100) {
                lp.add(rect.tl());
                lp.add(rect.br());
            }

        }

        // we found something - it is digit
        if (!lp.isEmpty()) {
            MatOfPoint points = new MatOfPoint();
            points.fromList(lp);

            Rect rect = boundingRect(points);

            // opencv bug fix - it returning bigger rectangle then image
            if(rect.x+rect.width >= input.width())
                rect.x = input.width() - rect.width;
            if(rect.y+rect.height >= input.height())
                rect.y = input.height() - rect.height;
            if(rect.x < 0) rect.x = 0;
            if(rect.y < 0) rect.y = 0;
            if(rect.width > input.width()) rect.width = input.width();
            if(rect.height > input.height()) rect.height = input.height();

            return Optional.of(rect);
        }

        return Optional.empty();

    }
}
