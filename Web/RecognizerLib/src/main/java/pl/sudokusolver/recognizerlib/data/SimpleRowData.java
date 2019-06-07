package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.*;
import org.opencv.ml.Ml;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class SimpleRowData implements IData{
    /**
     * Samples
     */
    private Mat samples;

    /**
     * Labels stored in {@link pl.sudokusolver.recognizerlib.data.DataType#Simple} type.
     */
    private Mat labels;

    /**
     * Size of single sample (It is rectangle which have size of sampleSize x sampleSize).
     */
    private short sampleSize;

    /**
     * Creates object using given parameters.
     * @param samples samples.
     * @param labels labels.
     * @param sampleSize size of single sample.
     */
    public SimpleRowData(Mat samples, Mat labels, short sampleSize){
        this.samples = samples;
        this.labels = labels;
        this.sampleSize = sampleSize;
    }

    /**
     * Creates object using sheet file passed by path.
     * @param url absolute path to sheet file.
     * @param size size of single sample.
     * @throws CvException if couldn't open file.
     */
    public SimpleRowData(String url, short size) throws CvException {
        loadFromSheet(url,size);
    }

    /**
     * @param url
     * @param size
     * @throws CvException
     */
    private void loadFromSheet(String url, short size) throws CvException {
        //todo: check if file exist
        Mat img = imread(url, IMREAD_UNCHANGED);

        int cols = img.width() / size;
        int rows = img.height() / size;

        int totalPerClass = cols * rows / 10;

        samples = Mat.zeros(cols * rows, size * size, CvType.CV_32FC1);
        labels = Mat.zeros(cols * rows, 1, CvType.CV_32FC1);

        Size cellSize = new Size(size, size);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                Rect rect = new Rect(new Point(j * size, i * size), cellSize);
                int currentCell = i * cols + j;
                double label = (j + i * cols) / totalPerClass;

                // Skip '0'
                if (label == 0) continue;

                Mat cell = ImageProcessing.deskew(new Mat(img, rect), size);
                Mat procCell = ImageProcessing.procSimple(cell, size);

                for (int k = 0; k < size * size; k++)
                    samples.put(currentCell, k, procCell.get(0, k));

                labels.put(currentCell, 0 , label);

            }
        sampleSize = size;
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
     * @return M1.ROW_SAMPLE. Więcej informacji możesz znaleźć na <a href="https://docs.opencv.org/4.0.1/javadoc/org/opencv/ml/Ml.html">openCV</a>
     */
    @Override
    public int getSampleType() {
        return Ml.ROW_SAMPLE;
    }

    @Override
    public short getSize() {
        return sampleSize;
    }

    /**
     * @return {@link pl.sudokusolver.recognizerlib.data.DataType#Simple}
     */
    @Override
    public DataType getDataType() { return DataType.Simple; }

}
