package pl.sudokusolver.recognizerlib.data;

import com.google.common.collect.Lists;
import org.opencv.core.*;
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

/**
 * Now unused.
 * Class used for keeping learning data.<br>
 */
public class LearnData implements IData {
    /**
     * Samples (images).
     */
    private Mat samples;

    /**
     * Labels which are stored using {@link pl.sudokusolver.recognizerlib.data.DataType#Simple}.
     */
    private Mat labels;

    /**
     * Size of single sample. Samples have to be rectangular.<br>
     * So whole image takes <code>sampleSize * sampleSize</code>px.
     */
    private short sampleSize;

    /**
     * @param files list which contains paths to 9 images. On each photo there are other digits.<br>
     *              Files should be order by digit which have.<br>
     *              Images need to be only black and white (digits should be white).
     * @throws CvException if can't open one of files.
     * @throws IllegalArgumentException if list size is different then 9.
     */
    public LearnData(List<String> files) throws CvException, IllegalArgumentException {
        if(files.size() != 9) throw new IllegalArgumentException("LearnData need 9 files");
        List<List<Rect>> allSamples = new LinkedList<>();
        int numberOfRect = 0;

        // extraction digit form images
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


        // saving extracted images to matrix
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

    /**
     * @return M1.ROW_SAMPLE. For more information, you can checkout <a href="https://docs.opencv.org/4.0.1/javadoc/org/opencv/ml/Ml.html">openCV</a>
     */
    @Override
    public int getSampleType() {
        return Ml.ROW_SAMPLE;
    }

    /**
     * @return 28 if object was created correctly, else 0.
     */
    @Override
    public short getSize() {
        return sampleSize;
    }

    /**
     * @return {@link pl.sudokusolver.recognizerlib.data.DataType#Simple}
     */
    @Override
    public DataType getDataType() {
        return DataType.Simple;
    }
}
