package pl.sudokusolver.sudokurecognizerlib.dataproviders;

import org.opencv.core.*;
import org.opencv.ml.Ml;
import pl.sudokusolver.sudokurecognizerlib.imageprocessing.ImageProcessing;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class RowData implements IData{
    private  Mat samples;
    private Mat labels;
    public short sampleSize;

    public RowData(){}

    public RowData(String url, short size){
        loadFromFile(url,size);
    }

    public void loadFromFile(String url, short size) {
        Size cellSize = new Size(size, size);
        Mat img = imread(url, IMREAD_UNCHANGED);

        int cols = img.width() / size;
        int rows = img.height() / size;

        int totalPerClass = cols * rows / 10;

        samples = Mat.zeros(cols * rows, size * size, CvType.CV_32FC1);
        labels = Mat.zeros(cols * rows, 1, CvType.CV_32FC1);

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
    }

    public Mat getData() {
        return samples;
    }

    public Mat getLabels() {
        return labels;
    }

    public int getType() {
        return Ml.ROW_SAMPLE;
    }

    public short getSize() {
        return sampleSize;
    }
}
