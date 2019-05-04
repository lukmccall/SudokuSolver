package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;

/**
 * Filter zmieniający kolory obrazu na czarno-białe
 * <p>
 *     <b>Uwaga</b><br>
 *     Zdjęcie wejściowe nie może być już czarno-białe
 * </p>
 */
public class ToGrayFilter implements IFilter {
    @Override
    public void apply(Mat input) {
        cvtColor(input, input, COLOR_BGR2GRAY);
    }
}
