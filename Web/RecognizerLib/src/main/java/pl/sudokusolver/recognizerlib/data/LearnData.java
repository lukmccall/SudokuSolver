package pl.sudokusolver.recognizerlib.data;

import com.google.common.collect.Lists;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.ml.Ml;
import pl.sudokusolver.recognizerlib.filters.BlurFilter;
import pl.sudokusolver.recognizerlib.filters.ResizeFilter;
import pl.sudokusolver.recognizerlib.filters.ToGrayFilter;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import java.util.LinkedList;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class LearnData implements IData {
    private Mat samples;
    private Mat labels;
    private short sampleSize;

    public LearnData(List<String> files){
        List<List<Rect>> allSamples = new LinkedList<>();
        int numberOfRect = 0;

        for(int i = 0; i < files.size(); i++){
            allSamples.add(new LinkedList<>());
            Mat img = imread(files.get(i),1);
            new ToGrayFilter().apply(img);
            new BlurFilter().apply(img);

            List<MatOfPoint> countours = Lists.newArrayList();
            findContours(img, countours, new Mat(), RETR_TREE , CHAIN_APPROX_SIMPLE);
            for (MatOfPoint countour : countours) {
                Rect boundingRect = boundingRect(countour);
                if (boundingRect.height > 28 && boundingRect.width < 60 && boundingRect.height < 50)
                    allSamples.get(i).add(boundingRect);
            }
            numberOfRect += allSamples.get(i).size();
        }
        sampleSize = 28;
        samples = Mat.zeros(numberOfRect, sampleSize*sampleSize, CvType.CV_32FC1);
        labels = Mat.zeros(numberOfRect, 1, CvType.CV_32FC1);


        int currSampleIndex = 0;
        for(int i = 0; i < files.size(); i++){
            Mat img = imread(files.get(i), IMREAD_UNCHANGED);
            new ToGrayFilter().apply(img);
            for(Rect rect : allSamples.get(i)){
                Mat cell = new Mat(img, rect);
                new ResizeFilter(sampleSize).apply(cell);
                Mat procCell = ImageProcessing.procSimple(cell, sampleSize);

                for (int k = 0; k < sampleSize * sampleSize; k++)
                    samples.put(currSampleIndex, k, procCell.get(0, k));
                labels.put(currSampleIndex, 0, i+1);
                currSampleIndex++;
            }
        }
    }

    @Override
    public Mat getData() {
        return samples;
    }

    @Override
    public Mat getLabels() {
        return labels;
    }

    @Override
    public int getSampleType() {
        return Ml.ROW_SAMPLE;
    }

    @Override
    public short getSize() {
        return sampleSize;
    }

    @Override
    public DataType getDataType() {
        return DataType.Simple;
    }
}
