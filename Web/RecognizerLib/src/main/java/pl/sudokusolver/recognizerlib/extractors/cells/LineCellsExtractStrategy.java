package pl.sudokusolver.recognizerlib.extractors.cells;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.TermCriteria;
import pl.sudokusolver.recognizerlib.utility.comparators.CenterLinesComparator;
import pl.sudokusolver.recognizerlib.utility.Pair;
import pl.sudokusolver.recognizerlib.utility.comparators.PointComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.opencv.core.Core.KMEANS_RANDOM_CENTERS;
import static org.opencv.core.Core.kmeans;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.HoughLines;

public class LineCellsExtractStrategy implements CellsExtractStrategy{
    @Override
    public List<Mat> getCells(Mat grid) {
        Mat canimg = new Mat(grid.size(), grid.type());
        Canny(grid, canimg, 30, 90);

        Mat lines = new Mat();
        HoughLines(canimg, lines, 1, Math.PI / 180, 100);

        Pair<List<Point>, List<Point>> linePoints = calcLinesPoint(lines);
        List<Point> hpoints = linePoints.getFirst();
        List<Point> vpoints = linePoints.getSecond();


        Mat hPointsMat = packPointsToMat(hpoints);
        Mat vPointsMat = packPointsToMat(vpoints);

        for (int i = 0; i < hpoints.size(); i++)
            hPointsMat.put(i, 0, hpoints.get(i).x, hpoints.get(i).y);

        for (int i = 0; i < vpoints.size(); i++)
            vPointsMat.put(i, 0, vpoints.get(i).x, vpoints.get(i).y);

        Mat hOutput = new Mat(hpoints.size(), 2 , CV_32F);
        Mat vOutput = new Mat(vpoints.size(), 2 , CV_32F);

        if (vpoints.size() >= 10 && hpoints.size() >= 10) {
            kmeansWrapper(hPointsMat, hOutput);
            kmeansWrapper(vPointsMat, vOutput);

            List<Point> hlines = matToList(hOutput, 10);
            List<Point> vlines = matToList(vOutput, 10);

            Collections.sort(hlines, new CenterLinesComparator());
            Collections.sort(vlines, new CenterLinesComparator());


            if (checkLines(vlines, hlines)) {
                List<Point> points = getPoint(vlines, hlines);
                if (points.size() != 100) return null;
                Collections.sort(points, new PointComparator());
//                for debugging
//                int z = 0;
//                for(Point p : points){
//                    putText(grid,  new Integer(z).toString(), p, 0, 0.3,  new Scalar(255,0,0), 1);
//                    z++;
//                }
//                imshow("points", grid);
//                waitKey();
                return cutGrid(grid, points);

            }

        }
        return null;
    }

    private List<Mat> cutGrid(Mat grid, List<Point> interPoints){
        List<Mat> list = new ArrayList<>(81);
        for (int i = 0; i < interPoints.size() - 11; i++) {
            int ri = i / 10;
            int ci = i % 10;
            if (ci != 9 && ri != 9) {
                Point get = interPoints.get(i);
                Point get2 = interPoints.get(i + 11);
                Rect r1 = new Rect(get, get2);

                if ((r1.x + r1.width <= grid.cols()) && (r1.y + r1.height <= grid.rows()) && r1.x >= 0 && r1.y >= 0) {
                    Mat s = new Mat(grid, r1).clone();
                    list.add(s);
                }
            }
        }
        return list;
    }

    private void kmeansWrapper(Mat input, Mat output){
        Mat labels = Mat.zeros(input.rows(), 1, CV_8U);
        kmeans(input, 10, labels, new TermCriteria(TermCriteria.EPS, 10, 1), 20, KMEANS_RANDOM_CENTERS, output);
    }

    private Mat packPointsToMat(List<Point> points){
        Mat ret = new Mat(points.size(), 2, CV_32F);
        for (int i = 0; i < points.size(); i++)
            ret.put(i, 0, points.get(i).x, points.get(i).y);
        return ret;
    }

    private Pair<List<Point>, List<Point>> calcLinesPoint(Mat lines ){
        List<Point> hpoints = new ArrayList<>();
        List<Point> vpoints = new ArrayList<>();

        for (int i = 0; i < lines.rows(); i++) {
            double[] data = lines.get(i, 0);

            if (Math.sin(data[1]) > 0.8)
                hpoints.add(new Point(data[0], data[1]));
            else if (Math.cos(data[1]) > 0.8)
                vpoints.add(new Point(data[0], data[1]));
        }
        return new Pair<>(hpoints, vpoints);

    }

    private List<Point> getPoint(List<Point> vlines, List<Point> hlines) {
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

    private double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    private Point parametricIntersect(Double r1, Double t1, Double r2, Double t2) {
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

    private List<Point> matToList(Mat mat, int size){
        List<Point> ret = new ArrayList<>(size);
        for(int i = 0; i < size; i++)
            ret.add(new Point(mat.get(i,0)[0],mat.get(i,1)[0]));
        return ret;
    }

    private boolean checkLines(List<Point> vlines, List<Point> hlines) {
        final int diff = 30; // todo: use this var

        if (!(vlines.size() == 10 && hlines.size() == 10)) {
            return false;
        }
        if (checkPointDiff(hlines, diff)) return false;
        if (checkPointDiff(vlines, diff)) return false;
        return true;
    }

    private boolean checkPointDiff(List<Point> points, int diff) {
        for (int i = 0; i < points.size() - 1; i++) {
            double r1 = points.get(i).x;
            double r2 = points.get(i + 1).x;
            if (Math.abs(r1 - r2) < diff) {
                return true;
            }
        }
        return false;
    }
}
