package pl.sudokusolver.recognizerlib.utility.staticmethods;

import com.google.common.collect.ImmutableList;
import org.opencv.core.*;
import org.opencv.imgproc.Moments;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.imgproc.Imgproc.*;

/**
 * Set of functions for image processing purpose.<br>
 * Same of them was created by: <br>
 * -José Luis Diaz (github: <a href="https://github.com/joseluisdiaz/sudoku-solver">joseluisdiaz</a>)
 *
 */
public class ImageProcessing {
    /**
     * Rotating image.
     * @param img input matrix
     * @param size size of output
     * @return rotated and rescaled matrix
     */
    public static Mat deskew(Mat img, short size) {
        Moments m = moments(img);

        if (Math.abs(m.get_mu02()) < 0.01) {
            return img.clone();
        }
        Mat result = new Mat(img.size(), CV_32FC1);
        double skew = m.get_mu11() / m.get_mu02();
        Mat M = new Mat(2, 3, CV_32FC1);

        M.put(0, 0, 1, skew, -0.5 * size * skew, 0, 1, 0);

        warpAffine(img, result, M, new Size(size, size), WARP_INVERSE_MAP | INTER_LINEAR);

        return result;
    }

    /**
     * "Flating" and normalizing matrix
     * @param img input matrix
     * @param size size of output
     * @return "falated" input matrix. It has <code>one</code> row and <code>size * size</code> cols.<br>
     * Output matrix is type of <code>CV_32FC1</code>
     */
    public static Mat procSimple(Mat img, short size) {
        Mat result = Mat.zeros(1, size * size, CV_32FC1);

        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                int nro = size * row+col;
                double value = img.get(row, col)[0] / 255.0; // normalizing
                result.put(0, nro, value);
            }
        }

        return result;
    }

    /**
     * @param digit input matrix
     * @param size size of output
     * @return centered input matrix (size of <code>size * size</code>)
     */
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

        warpAffine(digit, res, A, new Size(size, size), WARP_INVERSE_MAP | INTER_LINEAR);
        return res;
    }

    /**
     * Apply mask to matrix
     * @param image input matrix
     * @param poly mask
     * @return result of applying mask to matrix
     */
    public static Mat applyMask(Mat image, MatOfPoint poly) {
        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);

        drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(255), -1);
        drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(0), 2);

        Mat dst = new Mat();
        image.copyTo(dst, mask);

        mask.release();
        return dst;
    }

    /**
     * This method resize, using proportion, image to be smaller then max size.
     * @param image matrix to resize.
     * @param maxSize max size of output matrix.
     */
    public static void resizeToMaxSize(Mat image, Size maxSize){
        double height = image.size().height;
        double width = image.size().width;

        double ratio;

        if( height <= maxSize.height && width <= maxSize.width )
            ratio = 1;
        else if (height >= width)
            ratio = maxSize.height / height;
        else if (width > height)
            ratio = maxSize.width / width;
        else ratio = 1;

        resize(image,image, new Size((int)(ratio * width), (int)(ratio * height)));
    }

    /**
     * You shouldn't be able to create instance of this class.
     */
    private ImageProcessing(){}

}
