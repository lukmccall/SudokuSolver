package pl.sudokusolver.recognizerlib.filters;

import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class SaveFilter implements IFilter {
    private String path;

    public SaveFilter(String path){
        this.path = path;
    }

    @Override
    public void apply(Mat input) {
        imwrite(path, input);
    }
}
