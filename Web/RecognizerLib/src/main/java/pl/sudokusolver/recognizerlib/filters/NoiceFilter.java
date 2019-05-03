package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class NoiceFilter implements IFilter {

    @Override
    public void apply(Mat input){
        List<MatOfPoint> contours = new ArrayList<>();
        Mat tmp = new Mat(input.size(), input.type());
        input.copyTo(tmp);

        Imgproc.findContours(tmp, contours, new Mat(), Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
            Rect r = Imgproc.boundingRect(contours.get(i));
            if (isNoise(r.width, r.height, input.cols(), input.rows())) {
                Imgproc.drawContours(input, contours, i, new Scalar(0), Core.FILLED);
            }
        }

    }

    private static  boolean isNoise(int width, int height, int tileWidth, int tileHeight) {
        if (width < tileWidth / 10 || height < tileHeight / 10) {
            return true;
        }
        return false;
    }
}
