package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.deeplearning4j.clustering.cluster.Cluster;
import org.deeplearning4j.clustering.cluster.ClusterSet;
import org.deeplearning4j.clustering.kmeans.KMeansClustering;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.CV_PI;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.*;

class LinesComparator implements Comparator<Cluster> {
    @Override
    public int compare(Cluster t1, Cluster t2) {
        return Integer.valueOf((int)t1.getCenter().getArray().getFloat(0)).compareTo(Integer.valueOf((int) t2.getCenter().getArray().getFloat(0)));
    }
}

@ExtendWith({_INIT_.class})
class GridImgTest {
    private static boolean showIMG = false;

    void getSudoku(String url) throws NotFoundSudokuExceptions {
        GridImg gridImg = new GridImg(url);
        if (showIMG) {
            resize(gridImg.getImg(), gridImg.getImg(), new Size(600, 600));
            imshow(url, gridImg.getImg());
            waitKey();
        }

    }

    @Test
    void getSudokuImg() {
        try {
            List<String> images = _TestUtility_.getAllImages();
            for (String img : images) {
                getSudoku(img);
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    void test2() {
        List<String> images = null;
        try {
            images = _TestUtility_.getAllImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GridImg gridImg = null;
        try {
            gridImg = new GridImg(images.get(0));
        } catch (NotFoundSudokuExceptions notFoundSudokuExceptions) {
            notFoundSudokuExceptions.printStackTrace();
        }

        BufferedImage bf = matToBufferedImage(gridImg.getImg());
        org.bytedeco.javacpp.opencv_core.Mat procimg = bufferedImageToMat(bf);
        org.bytedeco.javacpp.opencv_core.Mat canimg = new org.bytedeco.javacpp.opencv_core.Mat(procimg.size());
        org.bytedeco.javacpp.opencv_imgproc.Canny(procimg, canimg, 30, 90);


        org.bytedeco.javacpp.opencv_core.Mat lines = new org.bytedeco.javacpp.opencv_core.Mat();//vector stores the parameters (rho,theta) of the detected lines
        //HoughLines(canimg, lines, 1, CV_PI / 180, 70,1,1, 0, CV_PI);
        org.bytedeco.javacpp.opencv_imgproc.HoughLines(canimg, lines, 1, CV_PI / 180, 100);

        FloatRawIndexer srcIndexer = lines.createIndexer();

        /*Horizontal lines and one for vertical lines*/
        List<org.deeplearning4j.clustering.cluster.Point> hpoints = new ArrayList<>();
        List<org.deeplearning4j.clustering.cluster.Point> vpoints = new ArrayList<>();

        for (int i = 0; i < srcIndexer.rows(); i++) {
            float[] data = new float[2]; //data[0] is rho and data[1] is theta
            srcIndexer.get(0, i, data);
            double d[] = {data[0], data[1]};
            if (Math.sin(data[1]) > 0.8) {//horizontal lines have a sin value equals 1, I just considered >.8 is horizontal line.
                hpoints.add(new org.deeplearning4j.clustering.cluster.Point("hrho" + Math.sin(data[1]), "hrho", d));
            } else if (Math.cos(data[1]) > 0.8) {//vertical lines have a cos value equals 1,
                vpoints.add(new org.deeplearning4j.clustering.cluster.Point("vrho" + Math.cos(data[1]), "vrho", d));
            }
        }

        /*Cluster vertical and horizontal lines into 10 lines for each using kmeans with 10 iterations*/
        KMeansClustering kmeans = KMeansClustering.setup(10, 10, "euclidean");

        if (vpoints.size() >= 10 && hpoints.size() >= 10) {
            ClusterSet hcs = kmeans.applyTo(hpoints);
        }
    }

    @Test
    void test() {
        List<String> images = null;
        try {
            images = _TestUtility_.getAllImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GridImg gridImg = null;
        try {
            gridImg = new GridImg(images.get(0));
        } catch (NotFoundSudokuExceptions notFoundSudokuExceptions) {
            notFoundSudokuExceptions.printStackTrace();
        }

        Mat canimg = new Mat(gridImg.getImg().size(), gridImg.getImg().type());

        Mat procimg = gridImg.getImg().clone();

        Canny(gridImg.getImg(), canimg, 30, 90);

        Mat lines = new Mat();//vector stores the parameters (rho,theta) of the detected lines
        //HoughLines(canimg, lines, 1, CV_PI / 180, 70,1,1, 0, CV_PI);
        HoughLines(canimg, lines, 1, CV_PI / 180, 100);

        /*Horizontal lines and one for vertical lines*/
        List<org.deeplearning4j.clustering.cluster.Point> hpoints = new ArrayList<>();
        List<org.deeplearning4j.clustering.cluster.Point> vpoints = new ArrayList<>();

        for (int i = 0; i < lines.rows(); i++) {
            double[] data;
            data = lines.get(i, 0);

            double d[] = {data[0], data[1]};
            if (Math.sin(data[1]) > 0.8) {//horizontal lines have a sin value equals 1, I just considered >.8 is horizontal line.
                hpoints.add(new org.deeplearning4j.clustering.cluster.Point("hrho" + Math.sin(data[1]), "hrho", data));
            } else if (Math.cos(data[1]) > 0.8) {//vertical lines have a cos value equals 1,
                vpoints.add(new org.deeplearning4j.clustering.cluster.Point("vrho" + Math.cos(data[1]), "vrho", data));
            }
        }


        KMeansClustering kmeans = KMeansClustering.setup(10, 10, "euclidean");

        System.out.println("Lines Number " + vpoints.size() + " " + hpoints.size());

        if (vpoints.size() >= 10 && hpoints.size() >= 10) {
            ClusterSet hcs = kmeans.applyTo(hpoints);

            List<Cluster> hlines = hcs.getClusters();
            Collections.sort(hlines, new LinesComparator());

            ClusterSet vcs = kmeans.applyTo(vpoints);
            List<Cluster> vlines = vcs.getClusters();
            Collections.sort(vlines, new LinesComparator());

            if (checkLines(vlines, hlines)) {
                System.out.print("cos2");
                List<Point> points = getPoint(vlines, hlines);
                if (points.size() != 100) {
                    //break to get another image if number of points not equal 100
                    Assert.fail("cos poszeło nie tak");
                }

                for (Point point : points) {
                    circle(procimg, point, 10, new Scalar(255, 0, 0, 255), 10, 8, 0);
                }

                vlines.addAll(hlines);//appen hlines to vlines to print them in one for loop
                for (int i = 0; i < vlines.size(); i++) {
                    Cluster get = vlines.get(i);
                    double rho = get.getCenter().getArray().getDouble(0);
                    double theta = get.getCenter().getArray().getDouble(1);
                    double a = Math.cos(theta), b = Math.sin(theta);
                    double x0 = a * rho, y0 = b * rho;
                    opencv_core.CvPoint pt1 = new opencv_core.CvPoint((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a)));
                    opencv_core.CvPoint pt2 = new opencv_core.CvPoint((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
                    line(procimg, new Point(pt1.x(), pt1.y()),
                            new Point(pt2.x(), pt2.y()), new Scalar(0, 0, 0, 0), 3, 1, 0);

                }
            }

        }
        imshow("Test", procimg);
        waitKey();

    }


    private static boolean checkLines(List<Cluster> vlines, List<Cluster> hlines) {
        final int diff = 40;//this may vary if you change the image width and hieght in method warpPrespectivePuzzle (600)
        if (!(vlines.size() == 10 && hlines.size() == 10)) {
            return false;
        }
        for (int i = 0; i < hlines.size() - 1; i++) {
            Cluster get = hlines.get(i);
            int temp = '2';

            double r1 = get.getCenter().getArray().getDouble(0);
            Cluster get1 = hlines.get(i + 1);
            double r2 = get1.getCenter().getArray().getDouble(0);
            if (Math.abs(r1 - r2) < diff) {
                return false;
            }
        }
        for (int i = 0; i < vlines.size() - 1; i++) {
            Cluster get = vlines.get(i);
            double r1 = get.getCenter().getArray().getDouble(0);
            Cluster get1 = vlines.get(i + 1);
            double r2 = get1.getCenter().getArray().getDouble(0);
            if (Math.abs(r1 - r2) < diff) {
                return false;
            }
        }
        return true;
    }

    private static List<Point> getPoint(List<Cluster> vlines, List<Cluster> hlines) {
        List<Point> points = new ArrayList();
        for (int i = 0; i < hlines.size(); i++) {
            Cluster get = hlines.get(i);
            double r1 = get.getCenter().getArray().getDouble(0);
            double t1 = get.getCenter().getArray().getDouble(1);
            for (int j = 0; j < vlines.size(); j++) {
                Cluster get1 = vlines.get(j);
                double r2 = get1.getCenter().getArray().getDouble(0);
                double t2 = get1.getCenter().getArray().getDouble(1);
                Point o = parametricIntersect(r1, t1, r2, t2);
                if (o.y != -1 & o.x != -1) {
                    points.add(o);
                }
            }
        }
        for (int i = 0; i < points.size() - 1; i++) {
            Point get = points.get(i);
            Point get1 = points.get(i + 1);
            if (getDistance(get, get1) < 20) {
                points.remove(get);
            }
        }
        //System.out.println("Points Size" + points.size());
        return points;
    }


    /*get intersection points between two lines given their rhoes and thetas*/
    private static Point parametricIntersect(Double r1, Double t1, Double r2, Double t2) {
        double ct1 = Math.cos(t1);     //matrix element a
        double st1 = Math.sin(t1);     //b
        double ct2 = Math.cos(t2);     //c
        double st2 = Math.sin(t2);     //d
        double d = ct1 * st2 - st1 * ct2;//determinative (rearranged matrix for inverse)
        if (d != 0.0f) {
            int x = (int) ((st2 * r1 - st1 * r2) / d);
            int y = (int) ((-ct2 * r1 + ct1 * r2) / d);
            return new Point(x, y);
        } else { //lines are parallel and will NEVER intersect!
            return new Point(-1, -1);
        }
    }

    static double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    public static BufferedImage matToBufferedImage(org.opencv.core.Mat frame) {
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width() ,frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);
        return image;
    }

    public org.bytedeco.javacpp.opencv_core.Mat bufferedImageToMat(BufferedImage bi) {
        OpenCVFrameConverter.ToMat cv = new OpenCVFrameConverter.ToMat();
        return cv.convertToMat(new Java2DFrameConverter().convert(bi));
    }
}