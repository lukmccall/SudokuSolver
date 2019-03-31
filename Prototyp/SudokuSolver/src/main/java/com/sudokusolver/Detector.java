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

import static org.opencv.core.CvType.*;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.moments;
import static org.opencv.imgproc.Imgproc.warpAffine;

public class Detector {
    public static final String DIGITS = "digits.png";
    public static final int SZ = 20;
    public KNearest knn;
    public ANN_MLP ann_mlp;
	public SVM		svm;

    public void learnKNN(){
        Size cellSize = new Size(SZ, SZ);
        Mat img = imread(DIGITS, IMREAD_UNCHANGED);

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
                labels.put(currentCell, 0 , label);

            }
        }

        knn = KNearest.create();
        knn.train(samples, Ml.ROW_SAMPLE, labels);
    }

    public void learnKNNFromMNIST(){
        TrainData d = MNISTReader.getKNNData();
        knn = KNearest.create();
        knn.train(d.data, Ml.ROW_SAMPLE, d.labels);
    }
    public void loadANN(){
        ann_mlp = ANN_MLP.load("ann.xml");
    }
    public void learnANN(){
        Size cellSize = new Size(SZ, SZ);
        Mat img = imread(DIGITS, IMREAD_UNCHANGED);

        int cols = img.width() / 20;
        int rows = img.height() / 20;

        int totalPerClass = cols * rows / 10;

        Mat samples = Mat.zeros(cols * rows, SZ * SZ, CvType.CV_32FC1);
        Mat labels = Mat.zeros(cols * rows, 9, CvType.CV_32FC1);

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
                String word = "";
                if(label == 9) word = "000000001";
                if(label == 8) word = "000000010";
                if(label == 7) word = "000000100";
                if(label == 6) word = "000001000";
                if(label == 5) word = "000010000";
                if(label == 4) word = "000100000";
                if(label == 3) word = "001000000";
                if(label == 2) word = "010000000";
                if(label == 1) word = "100000000";

                for(int k = 0; k < 9; k++){
                    double c = (word.charAt(k) == '1') ? 1 : 0;
                    labels.put(currentCell, k, c);
                }

            }
        }
        ann_mlp = ANN_MLP.create();

        Mat layers = new Mat(1 , 4 , CV_32FC1);
        layers.put(0, 0, SZ*SZ);
        layers.put(0, 1, 50);
        layers.put(0, 2, 20);
        layers.put(0, 3, 9);
        ann_mlp.setLayerSizes(layers);
        ann_mlp.setActivationFunction(ANN_MLP.SIGMOID_SYM);

        ann_mlp.train(samples, Ml.ROW_SAMPLE, labels);
    }
	
	public void loadSVM(){
        svm = SVM.load("svm.yml");
    }

    public Detector(){
//        learnANN();
        //loadANN();
		loadSVM();
//        learnKNNFromMNIST();
//        learnKNN();
    }

    public static Mat deskew(Mat img) {
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

    public static Mat procSimple(Mat img) {
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

    public static Mat center(Mat digit) {
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

	 public int detectSVM(Mat digit){
        Mat wraped = deskew(center(digit.clone()));
        Mat result = new Mat();


        svm.predict(procSimple(wraped), result);
        return (int)result.get(0,0)[0];
    }
	
    public int detectKNN(Mat digit){
        Mat wraped = deskew(center(digit.clone()));
        Mat result = new Mat();
        Mat neighborhood = new Mat();
        Mat distances = new Mat();

        knn.findNearest(procSimple(wraped), 3, result, neighborhood, distances);
        return (int)result.get(0,0)[0];
    }

    public int detectANN(Mat digit) {
        Mat wraped = deskew(center(digit.clone()));
        Mat result = new Mat();
        ann_mlp.predict(procSimple(wraped), result);
        int pre = 0;
        for(int i = 1; i < 10; i++)
            if(result.get(0,pre)[0] < result.get(0,i)[0])
                pre = i;
        return pre;
    }
}
