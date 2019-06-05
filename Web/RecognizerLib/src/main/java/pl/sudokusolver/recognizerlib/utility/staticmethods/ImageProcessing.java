package pl.sudokusolver.recognizerlib.utility.staticmethods;

import com.google.common.collect.ImmutableList;
import org.opencv.core.*;
import org.opencv.imgproc.Moments;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.imgproc.Imgproc.*;

/**
 * Zbiór funkcji operujących na zjdęciach.<br>
 * Autorem części z nich jest: <br>
 * -José Luis Diaz (github: <a href="https://github.com/joseluisdiaz/sudoku-solver">joseluisdiaz</a>)
 *
 */
public class ImageProcessing {
    /**
     * Metoda obracająca zdjęcia
     * @param img macierz z zdjęciem wejściowym
     * @param size rozmiar zdjęcia wyjściowego
     * @return obrócone zdjęcie wejściowe
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
     * @param img macierz z zdjęciem (musi to być macierz kwadratowa)
     * @param size rozmiar tego zdjęcia
     * @return macierz posiadająca tylko <code>jene</code> wiersz i <code>size * size</code> kolumn, w których
     *         ułożone są poszczególne komórki macierzy wejściowej. Wyjście jest typu CV_32FC1
     */
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

    /**
     * @param digit macierz z zdjęciem
     * @param size rozmiar zdjęcia wyjściowego
     * @return macierz z wycentrowanym zdjęciem wejściowym (<code>rozmiar to size * size</code>)
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
     * @param image macierz, ze zdjęciem wejściowym
     * @param poly maska w postaci macierzy
     * @return macierz wejściowej po aplikacji maski
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
     * @param image obraz do przeskalowania oraz obiekt wyjściowy
     * @param maxSize maksymalne wymiary jakie bedzie miało zdjęcie wyjściowe
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
     * Obiekt z metodami statycznymi.
     */
    private ImageProcessing(){}

}
