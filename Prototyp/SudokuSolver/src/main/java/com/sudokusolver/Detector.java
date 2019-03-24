package com.sudokusolver;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.ml.*;
import org.opencv.utils.Converters;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_32FC2;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.moments;
import static org.opencv.imgproc.Imgproc.warpAffine;

public class Detector {
    public static final String DIGITS = "C:/Users/LukMcCall/Desktop/digits.png";
    public static final int SZ = 20;
    private KNearest knn;

    public Detector(){
        URL file = getClass().getResource(DIGITS);

        Size cellSize = new Size(SZ, SZ);
        Mat img = imread(DIGITS, CV_LOAD_IMAGE_UNCHANGED);

        int cols = img.width() / 20;
        int rows = img.height() / 20;

        int totalPerClass = cols * rows / 10;

        Mat samples = Mat.zeros(cols * rows, SZ * SZ, CvType.CV_32FC1);
        Mat labels = Mat.zeros(cols * rows, 1, CvType.CV_32FC1);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                Rect rect = new Rect(new Point(j * SZ, i * SZ), cellSize);

                int currentCell = i * cols + j;
                double label = (j + i * cols) / totalPerClass;

                // HACK!
                if (label == 0) continue;

                Mat cell = deskew(new Mat(img, rect));
                Mat procCell = procSimple(cell);

                for (int k = 0; k < SZ * SZ; k++) {
                    samples.put(currentCell, k, procCell.get(0, k));
                }
                labels.put(currentCell, 0, label);

            }
        }

        knn = KNearest.create();
        knn.train(samples, Ml.ROW_SAMPLE, labels);

    }

    private Mat deskew(Mat img) {
        Moments m = moments(img);

        if (Math.abs(m.get_mu02()) < 0.01) {
            return img.clone();
        }
        Mat result = new Mat(img.size(), CV_32FC1);
        double skew = m.get_mu11() / m.get_mu02();
        Mat M = new Mat(2, 3, CV_32FC1);

        M.put(0, 0, 1, skew, -0.5 * SZ * skew, 0, 1, 0);

        warpAffine(img, result, M, new Size(SZ, SZ), Imgproc.WARP_INVERSE_MAP | Imgproc.INTER_LINEAR);

        return result;
    }

    private Mat procSimple(Mat img) {
        Mat result = Mat.zeros(1, SZ * SZ, CV_32FC1);

        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                int nro = SZ * row+col;
                double value = img.get(row, col)[0] / 255.0;
                result.put(0, nro, value);
            }
        }

        return result;
    }

    private Mat center(Mat digit) {
        Mat res = Mat.zeros(digit.size(), CV_32FC1);

        double s = 1.5*digit.height()/SZ;

        Moments m = moments(digit);

        double c1_0 = m.get_m10()/m.get_m00();
        double c1_1 = m.get_m01()/m.get_m00();

        double c0_0= SZ/2, c0_1 = SZ/2;

        double t_0 = c1_0 - s*c0_0;
        double t_1 = c1_1 - s*c0_1;

        Mat A = Mat.zeros( new Size(3, 2), CV_32FC1);

        A.put(0,0, s, 0, t_0);
        A.put(1,0, 0, s, t_1);

        warpAffine(digit, res, A, new Size(SZ, SZ), Imgproc.WARP_INVERSE_MAP | Imgproc.INTER_LINEAR);
        return res;
    }

    public int detect(Mat digit) {
        Mat wraped = deskew(center(digit.clone()));
        Mat result = new Mat();
        Mat neighborhood = new Mat();
        Mat distances = new Mat();

        knn.findNearest(procSimple(wraped), 3, result, neighborhood, distances);

        return (int)result.get(0,0)[0];
    }
}
