package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

/**
 * Helper. It show matrix (have to be valid img) on the screen.
 */
public class DisplayHelper implements IFilter {
    @Override
    public void apply(Mat input) {
        imshow("Debug", input);
        waitKey();
    }

    public void apply(Mat input, String path){
        imshow(path, input);
        waitKey();
    }
}
