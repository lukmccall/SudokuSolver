package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.resize;

/**
 * Helper w postaci filtra służący do wyświetlenie zdjęcia
 */
public class DisplayHelper implements IFilter {
    @Override
    public void apply(Mat input) {
        imshow("Debug", input);
        waitKey();
    }
}
