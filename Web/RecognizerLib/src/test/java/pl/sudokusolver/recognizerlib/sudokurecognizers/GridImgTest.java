package pl.sudokusolver.recognizerlib.sudokurecognizers;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
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

import static org.opencv.core.Core.KMEANS_RANDOM_CENTERS;
import static org.opencv.core.Core.kmeans;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.*;

class CenterLinesComparator implements Comparator<Point> {
    @Override
    public int compare(Point t1, Point t2) {
        return Integer.compare((int) t1.x, (int) t2.y);
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
    void newImple(){
        List<String> images = null;
        GridImg gridImg = null;
        try {
            images = _TestUtility_.getAllImages();
            gridImg = new GridImg(images.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundSudokuExceptions notFoundSudokuExceptions) {
            notFoundSudokuExceptions.printStackTrace();
        }

        Mat canimg = new Mat(gridImg.getImg().size(), gridImg.getImg().type());

        Mat procimg = gridImg.getImg().clone();

        Canny(gridImg.getImg(), canimg, 30, 90);

        Mat lines = new Mat();//vector stores the parameters (rho,theta) of the detected lines
        //HoughLines(canimg, lines, 1, CV_PI / 180, 70,1,1, 0, CV_PI);
        HoughLines(canimg, lines, 1, Math.PI / 180, 100);

        /*Horizontal lines and one for vertical lines*/
        List<Point> hpoints = new ArrayList<>();
        List<Point> vpoints = new ArrayList<>();

        for (int i = 0; i < lines.rows(); i++) {
            double[] data;
            data = lines.get(i, 0);

            double d[] = {data[0], data[1]};
            if (Math.sin(data[1]) > 0.8) {//horizontal lines have a sin value equals 1, I just considered >.8 is horizontal line.
                hpoints.add(new Point(data[0], data[1]));
            } else if (Math.cos(data[1]) > 0.8) {//vertical lines have a cos value equals 1,
                vpoints.add(new Point(data[0], data[1]));
            }
        }

        Mat hPointsMat = new Mat(hpoints.size(), 2, CV_32F);
        Mat hLabels = Mat.zeros(hpoints.size(), 1, CV_8U);

        Mat vPointsMat = new Mat(vpoints.size(), 2, CV_32F);
        Mat vLabels = Mat.zeros(vpoints.size(), 1, CV_8U);

        for (int i = 0; i < hpoints.size(); i++)
            hPointsMat.put(i, 0, hpoints.get(i).x, hpoints.get(i).y);

        for (int i = 0; i < vpoints.size(); i++)
            vPointsMat.put(i, 0, vpoints.get(i).x, vpoints.get(i).y);

        Mat hOutput = new Mat(hpoints.size(), 2 , CV_32F);
        Mat vOutput = new Mat(vpoints.size(), 2 , CV_32F);


        kmeans(hPointsMat, 10, hLabels, new TermCriteria(TermCriteria.EPS, 10, 1), 20, KMEANS_RANDOM_CENTERS, hOutput );
        kmeans(vPointsMat, 10, vLabels, new TermCriteria(TermCriteria.EPS, 10, 1), 20, KMEANS_RANDOM_CENTERS, vOutput );



        if (vpoints.size() >= 10 && hpoints.size() >= 10) {
            List<Point> hlines = matToList(hOutput, 10);
            List<Point> vlines = matToList(vOutput, 10);

            Collections.sort(hlines, new CenterLinesComparator());
            Collections.sort(vlines, new CenterLinesComparator());
            if (checkLines(vlines, hlines)) {
                List<Point> points = getPoint(vlines, hlines);
                if (points.size() != 100)  Assert.fail("cos poszeło nie tak");

                for (Point point : points)
                    circle(procimg, point, 10, new Scalar(255, 0, 0, 255), 10, 8, 0);

                vlines.addAll(hlines);//appen hlines to vlines to print them in one for loop
                for (int i = 0; i < vlines.size(); i++) {
                    double rho = vlines.get(i).x;
                    double theta = vlines.get(i).y;
                    double a = Math.cos(theta), b = Math.sin(theta);
                    double x0 = a * rho, y0 = b * rho;
                    Point pt1 = new Point((int) Math.round(x0 + 1000 * (-b)), (int) Math.round(y0 + 1000 * (a)));
                    Point pt2 = new Point((int) Math.round(x0 - 1000 * (-b)), (int) Math.round(y0 - 1000 * (a)));
                    line(procimg, new Point(pt1.x, pt1.y),
                            new Point(pt2.x, pt2.y), new Scalar(0, 0, 0, 0), 3, 1, 0);

                }
            }
        }
        imshow("Test", procimg);
        waitKey();

    }


    private static boolean checkLines(List<Point> vlines, List<Point> hlines) {
        final int diff = 40;//this may vary if you change the image width and hieght in method warpPrespectivePuzzle (600)
        if (!(vlines.size() == 10 && hlines.size() == 10)) {
            return false;
        }
        for (int i = 0; i < hlines.size() - 1; i++) {
            double r1 = hlines.get(i).x;
            double r2 = hlines.get(i + 1).x;
            if (Math.abs(r1 - r2) < diff) {
                return false;
            }
        }
        for (int i = 0; i < vlines.size() - 1; i++) {
            double r1 = vlines.get(i).x;
            double r2 = vlines.get(i + 1).x;
            if (Math.abs(r1 - r2) < diff) {
                return false;
            }
        }
        return true;
    }

    private static List<Point> getPoint(List<Point> vlines, List<Point> hlines) {
        List<Point> points = new ArrayList();
        for (int i = 0; i < hlines.size(); i++) {
            double r1 = hlines.get(i).x;
            double t1 = hlines.get(i).y;
            for (int j = 0; j < vlines.size(); j++) {
                double r2 = vlines.get(j).x;
                double t2 = vlines.get(j).y;
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

    private List<Point> matToList(Mat mat, int size){
        List<Point> ret = new ArrayList<>(size);
        for(int i = 0; i < size; i++)
            ret.add(new Point(mat.get(i,0)[0],mat.get(i,1)[0]));
        return ret;
    }
}