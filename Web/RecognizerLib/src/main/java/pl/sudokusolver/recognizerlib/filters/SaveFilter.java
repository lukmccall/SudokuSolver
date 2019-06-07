package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import static org.opencv.imgcodecs.Imgcodecs.IMWRITE_JPEG_QUALITY;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * This filter saving img to file.
 */
public class SaveFilter implements IFilter {
    /**
     * where you want to save image
     */
    private String path;

    /**
     * Set inner path variable.
     * @param path path where you want save file.
     */
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
