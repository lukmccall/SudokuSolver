package com.sudokusolver;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;

class LineDetector {
    /*
     * 'true' means row-iterator
     * 'false' mean col-iterator
     */
    private boolean aggregator;

    private static int TRIES = 6;
    private static int THRESHOLD = 2;

    private Mat m;

    private boolean found = false;
    private int nTry = 0;
    private int foundAt = 0;

    private Mat temp;

    private LineDetector(Mat m, boolean aggregator) {
        this.m = m;
        this.aggregator = aggregator;
    }

    /**
     * Creates a detector based on 'm' matrix, and iterate throw cols
     */
    static LineDetector col(Mat m) {
        return new LineDetector(m, false);
    }

    /**
     * Creates a detector based on 'm' matrix, and iterate throw rows
     */
    static LineDetector row(Mat m) {
        return new LineDetector(m, true);
    }

    public void step(int i) {

        if (nTry < TRIES) {
            temp = getVector(i);

            if (Core.countNonZero(temp) < THRESHOLD) {
                if (!found) {
                    foundAt = i;
                    found = true;
                }

                nTry++;
            } else if (found) {
                found = false;
            }
        }
    }

    private Mat getVector(int i) {
        return aggregator ? m.row(i) : m.col(i);
    }

    public int get() {
        return foundAt;
    }

}

public class Main {
    private static Detector detector;
    static {
        // takie cos co laduje takie drugie cos pobrane z https://opencv.org
        // stosunkowo wazne!!!
        // a i jeszcze trzeba dodac ^^ do java.library.path
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static  int distance(MatOfPoint2f poly) {
        Point[] a =  poly.toArray();
        return (int)Math.sqrt((a[0].x - a[1].x)*(a[0].x - a[1].x) +
                (a[0].y - a[1].y)*(a[0].y - a[1].y));
    }

    public static Mat applyMask(Mat image, MatOfPoint poly) {
        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);

        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(255), -1);
        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(0), 2);

        Mat dst = new Mat();
        image.copyTo(dst, mask);

        return dst;
    }

    public static final Ordering<Point> SORT = Ordering.natural().nullsFirst().onResultOf(
            new Function<Point, Integer>() {
                public Integer apply(Point foo) {
                    return (int) (foo.x+foo.y);
                }
            }
    );

    public static MatOfPoint2f orderPoints(MatOfPoint2f mat) {
        List<Point> pointList = SORT.sortedCopy(mat.toList());

        if (pointList.get(1).x > pointList.get(2).x) {
            Collections.swap(pointList, 1, 2);
        }

        MatOfPoint2f s = new MatOfPoint2f();
        s.fromList(pointList);

        return s;
    }

    public static Mat getSudoku(){
        Mat sudoku = imread("sudoku.jpg", IMREAD_UNCHANGED);
        Mat outerBox = new Mat();
        cvtColor(sudoku, outerBox, Imgproc.COLOR_RGB2GRAY);
        GaussianBlur(outerBox, outerBox, new Size(11,11), 0);
        adaptiveThreshold(outerBox, outerBox, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 5, 2);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat heirarchy = new Mat();
        findContours(outerBox, contours, heirarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);


        double area;
        double maxarea = 0;
        int p = 0;
        for (int i = 0; i < contours.size(); i++)
        {
            area = contourArea(contours.get(i), false);
            if (area > 50 )
            {
                if (area > maxarea)
                {
                    maxarea = area;
                    p = i;
                }
            }
        }
        MatOfPoint poly = new MatOfPoint(contours.get(p));
        MatOfPoint2f dst = new MatOfPoint2f();
        MatOfPoint2f src = new MatOfPoint2f();
        poly.convertTo(src, CvType.CV_32FC2);

        double arcLength = Imgproc.arcLength(src, true);
        approxPolyDP(src, dst, 0.02 * arcLength, true);
        int size = distance(dst);

        // drawContours(sudoku, contours, p, new Scalar(0,0,255), 10);
        Mat cutted = applyMask(sudoku, poly);

        MatOfPoint2f order = orderPoints(dst);


        Size reshape = new Size(size, size);

        Mat undistorted = new Mat(reshape, CvType.CV_8UC1);

        MatOfPoint2f d = new MatOfPoint2f();
        d.fromArray(new Point(0, 0), new Point(0, reshape.width), new Point(reshape.height, 0),
                new Point(reshape.width, reshape.height));

        warpPerspective(cutted, undistorted, getPerspectiveTransform(order, d), reshape);

        return undistorted;
    }

    public static List<Mat> getCells(Mat m) {
        int size = m.height() / 9;

        Size cellSize = new Size(size, size);
        List<Mat> cells = Lists.newArrayList();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Rect rect = new Rect(new Point(col * size, row * size), cellSize);

                Mat digit = new Mat(m, rect).clone();
                cells.add(digit);
            }
        }

        return cells;
    }

    public static Mat cleanLines(Mat image) {
        Mat m = image.clone();
        Mat lines = new Mat();

        int threshold = 80;
        int minLineSize = 200;
        int lineGap = 20;

        Imgproc.HoughLinesP(m, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);

        for (int x = 0; x < lines.rows(); x++) {
            double[] vec = lines.get(x, 0);
            double x1 = vec[0],
                    y1 = vec[1],
                    x2 = vec[2],
                    y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            line(m, start, end, Scalar.all(0), 3);

        }
        return m;
    }

    public static boolean CONTAIN_DIGIT_SUB_MATRIX_DENSITY (Mat input) {

        double tl = input.size().height/3;
        double br = input.size().width - input.size().width/3;


        Rect cut = new Rect(new Point(tl,tl), new Point(br,br));


        return Core.countNonZero(new Mat(input, cut)) > 20;
    }

    public static Optional<Rect> GET_DIGIT_BOX_CONTOURS(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();

        findContours(input.clone(), cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);

        List<Point> lp = Lists.newArrayList();

        for (MatOfPoint p : cont) {
            Rect rect = boundingRect(p);

            double aspect = rect.height / (double) rect.width;
            double area = rect.area();

            if (aspect > 0.5 && aspect < 10 && area > 20) {
                lp.add(rect.tl());
                lp.add(rect.br());
            }

        }

        if (!lp.isEmpty()) {
            MatOfPoint points = new MatOfPoint();
            points.fromList(lp);
            return Optional.of(boundingRect(points));
        }

        return Optional.absent();

    }

    public static Optional<Rect> GET_DIGIT_BOX_BYTE_SUM(Mat input) {
        List<MatOfPoint> cont = Lists.newArrayList();
        findContours(input.clone(), cont, new Mat(), RETR_CCOMP, CHAIN_APPROX_SIMPLE);
        Mat m = input.clone();

        for (MatOfPoint p : cont) {
            Rect rect = boundingRect(p);

            double aspect = rect.height / (double) rect.width;

            if (aspect < 0.1 || aspect > 10) {
                Imgproc.rectangle(m, rect.tl(), rect.br(), Scalar.all(0), -1);
            }

        }

        int center = m.rows() / 2;
        int n = m.rows()-1;

        LineDetector rowBottom = LineDetector.row(m);
        LineDetector rowTop = LineDetector.row(m);
        LineDetector colLeft = LineDetector.col(m);
        LineDetector colRight = LineDetector.col(m);

        for (int i = center; i <= n; i++) {
            rowBottom.step(i);
            rowTop.step(n-i);
            colLeft.step(i);
            colRight.step(n-i);
        }

        return Optional.of(new Rect(new Point(colLeft.get(), rowTop.get()), new Point(colRight.get()+1, rowBottom.get()+1)));
    }

    public static void main(String[] args){
        detector = new Detector();
        Mat sudoku = getSudoku();
        Mat proccesd = new Mat();
        cvtColor(sudoku, proccesd, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(proccesd, proccesd, new Size(11, 11), 0);

        adaptiveThreshold(proccesd, proccesd, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 2);
        proccesd = cleanLines(proccesd);

        List<Mat> cells = getCells(proccesd);
        int counter = 0;
        for(int i = 0; i < cells.size(); i++){
            Optional<Rect> box = GET_DIGIT_BOX_CONTOURS(cells.get(i));
            if (box.isPresent() && CONTAIN_DIGIT_SUB_MATRIX_DENSITY(cells.get(i))){
                counter++;
                Rect rect = box.get();
//                System.out.println(box.get() + " " + cells.get(i));
                if(rect.x+rect.width > cells.get(i).width()) rect.x = cells.get(i).width() - rect.width;
                if(rect.y+rect.height > cells.get(i).height()) rect.y = cells.get(i).height() - rect.height;
//                System.out.println(rect + " " + cells.get(i));
                Mat cutted = new Mat(cells.get(i), rect);
                System.out.println(detector.detect(cutted));
                imshow("cell",cutted);
                waitKey();
            }
        }

//        System.out.println(counter);
//        imshow("main", sudoku);
//        waitKey (30);
    }
}
