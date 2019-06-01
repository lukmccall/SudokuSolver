package pl.sudokusolver.recognizerlib.digitbox;

import com.google.common.collect.Lists;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib.utility.LineDetector;

import java.util.List;
import java.util.Optional;

import static org.opencv.imgproc.Imgproc.*;

/**
 * Implementacja algorytmu znajdującego cyfrę.
 * <p>
 * Korzysta on z metody <a href="https://docs.opencv.org/4.0.1/javadoc/org/opencv/imgproc/Imgproc.html#findContours-org.opencv.core.Mat-java.util.List-org.opencv.core.Mat-int-int-">findContours</a>.
 * Za pomocą, której znajduje wszytkie kontury, z których następnie liczone są ograniczające prostokąty.
 * Brane pod uwagę są tylko te, których współczynnik proporcji znajduje się w zakresie (0.1 , 10).
 * Następnie za pomocą detekcji lini wybierany jest odpowiedni prostokąt.
 * </p>
 */
public class DigitBoxByteSum implements IDigitBox {
    public Optional<Rect> getDigitBox(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();
        findContours(input.clone(), cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);
        Mat m = input.clone();

        for (MatOfPoint p : cont) {
            Rect rect = boundingRect(p);

            double aspect = rect.height / (double) rect.width;

            if (aspect < 0.1 || aspect > 10) {
                rectangle(m, rect.tl(), rect.br(), Scalar.all(0), -1);
            }

        }

        int center = m.rows() / 2;
        int n = m.rows()-1;

        LineDetector rowBottom = LineDetector.row(m);
        LineDetector rowTop = LineDetector.row(m);
        LineDetector colLeft = LineDetector.col(m);
        LineDetector colRight = LineDetector.col(m);

        for (int i = center; i <= n; i++) {
            rowBottom.step(i);
            rowTop.step(n-i);
            colLeft.step(i);
            colRight.step(n-i);
        }

        return Optional.of(new Rect(new Point(colLeft.get(), rowTop.get()), new Point(colRight.get()+1, rowBottom.get()+1)));
    }
}
