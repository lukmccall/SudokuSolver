package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.*;
import org.opencv.ml.Ml;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class SimpleRowData implements IData{
    /**
     * Próbki
     */
    private Mat samples;

    /**
     * Etykiety w formacie {@link pl.sudokusolver.recognizerlib.data.DataType#Simple}
     */
    private Mat labels;

    /**
     * Rozmiar pojedynczej próbki (jest to kwadrat sampleSize x sampleSize)
     */
    private short sampleSize;

    /**
     * Tworzy obiekt korzystając z podanych parametrów
     * @param samples próbki
     * @param labels etykiety
     * @param sampleSize rozmiar pojedynczej próbki
     */
    public SimpleRowData(Mat samples, Mat labels, short sampleSize){
        this.samples = samples;
        this.labels = labels;
        this.sampleSize = sampleSize;
    }

    /**
     * @param url absolutna scieżka do pliku
     * @param size rozmiar jednej cyfr w pliku
     * @throws CvException gdy nie udało się otworzyć pliku
     */
    public SimpleRowData(String url, short size) throws CvException {
        loadFromSheet(url,size);
    }

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
