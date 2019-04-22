package pl.sudokusolver.recognizerlib.imageprocessing;

import com.google.common.collect.ImmutableList;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import pl.sudokusolver.recognizerlib.utility.Utility;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.imgproc.Imgproc.*;

public class ImageProcessing {
    public static Mat deskew(Mat img, short size) {
        Moments m = moments(img);

        if (Math.abs(m.get_mu02()) < 0.01) {
            return img.clone();
        }
        Mat result = new Mat(img.size(), CV_32FC1);
        double skew = m.get_mu11() / m.get_mu02();
        Mat M = new Mat(2, 3, CV_32FC1);

        M.put(0, 0, 1, skew, -0.5 * size * skew, 0, 1, 0);

        warpAffine(img, result, M, new Size(size, size), Imgproc.WARP_INVERSE_MAP | Imgproc.INTER_LINEAR);

        return result;
    }

    public static Mat procSimple(Mat img, short size) {
        Mat result = Mat.zeros(1, size * size, CV_32FC1);

        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                int nro = size * row+col;
                double value = img.get(row, col)[0] / 255.0;
                result.put(0, nro, value);
            }
        }

        return result;
    }

    public static Mat center(Mat digit, short size) {
        Mat res = Mat.zeros(digit.size(), CV_32FC1);

        double s = 1.5*digit.height()/size;

        Moments m = moments(digit);

        double c1_0 = m.get_m10()/m.get_m00();
        double c1_1 = m.get_m01()/m.get_m00();

        double c0_0= size/2, c0_1 = size/2;

        double t_0 = c1_0 - s*c0_0;
        double t_1 = c1_1 - s*c0_1;

        Mat A = Mat.zeros( new Size(3, 2), CV_32FC1);

        A.put(0,0, s, 0, t_0);
        A.put(1,0, 0, s, t_1);

        warpAffine(digit, res, A, new Size(size, size), Imgproc.WARP_INVERSE_MAP | Imgproc.INTER_LINEAR);
        return res;
    }

    public static Mat applyMask(Mat image, MatOfPoint poly) {
        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);

        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(255), -1);
        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(0), 2);

        Mat dst = new Mat();
        image.copyTo(dst, mask);

        return dst;
    }

    public static Mat applyFilters(Mat img){
        Mat output = new Mat();
        cvtColor(img, output, Imgproc.COLOR_RGB2GRAY);
        GaussianBlur(output, output, new Size(11, 11), 0);
        adaptiveThreshold(output, output, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 5, 2);
        return output;
    }

}
