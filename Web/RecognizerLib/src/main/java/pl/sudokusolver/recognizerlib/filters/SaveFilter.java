package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import static org.opencv.imgcodecs.Imgcodecs.IMWRITE_JPEG_QUALITY;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class SaveFilter implements IFilter {
    private String path;

    public SaveFilter(String path){
        this.path = path;
    }

    @Override
    public void apply(Mat input) {

        MatOfInt params = new MatOfInt(2,1);
        params.put(0,0, IMWRITE_JPEG_QUALITY);
        params.put(1,0, 100);

        imwrite(path, input,params);
    }
}
