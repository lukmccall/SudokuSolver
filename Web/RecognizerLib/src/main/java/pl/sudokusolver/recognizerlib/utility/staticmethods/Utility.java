package pl.sudokusolver.recognizerlib.utility.staticmethods;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.recognizerlib.filters.IFilter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Collections;
import java.util.List;


/**
 * Helpers methods.
 */
public class Utility {
    /**
     * natural int comparator
     */
    private static final Ordering<Point> SORT = Ordering.natural().nullsFirst().onResultOf(
            (Function<Point, Integer>) foo -> (int) (foo.x+foo.y)
    );

    /**
     * @param mat natural matrix of points.
     * @return matrix which contains sorted by points.
     */
    public static MatOfPoint2f orderPoints(MatOfPoint2f mat) {
        List<Point> pointList = SORT.sortedCopy(mat.toList());

        if (pointList.get(1).x > pointList.get(2).x) {
            Collections.swap(pointList, 1, 2);
        }

        MatOfPoint2f s = new MatOfPoint2f();
        s.fromList(pointList);

        return s;
    }

    /**
     * @param poly natural matrix contains two point.
     * @return distance between two given points.
     */
    public static int distance(MatOfPoint2f poly) {
        Point[] a =  poly.toArray();
        return (int)Math.sqrt((a[0].x - a[1].x)*(a[0].x - a[1].x) +
                (a[0].y - a[1].y)*(a[0].y - a[1].y));
    }

    /**
     * Converted matrix to BufferedImage
     * @param frame matrix
     * @return buffor buffered image made from input matrix
     */
    public static BufferedImage matToBufferedImage(Mat frame) {
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

    /**
     * Apply all filters from list.
     * @param input input matrix.
     * @param filters list of filters.
     */
    public static void applyFilters(Mat input, List<IFilter> filters){
        if(filters != null) {
            for (IFilter filter : filters)
                filter.apply(input);
        }
    }

    /**
     * @return absolute path to ann dump.
     */
    public static String getANNDump(){
        return Init.class.getResource("/ann.xml").toString().substring(6);
    }

    /**
     * @return absolute path to svm dump.
     */
    public static String getSVMDump(){
        String r = Init.class.getResource("/svm.xml").toString();
        if(r.substring(0,3).equals("jar"))
            return r;
        return r.substring(6);
    }

    /**
     * @return absolute path to tesseract dump.
     */
    public static String getTessdata(){
        return Init.class.getResource("/tessdata/").toString().substring(6);
    }
}
