package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Class for reading data form MNIST format.<br>
 * It was crated using <a href="http://yann.lecun.com/exdb/mnist/">MNIST website</a>.<br>
 * You can also download data set from this website.
 */
public class MNISTReader {
    /**
     * Size of one sample.
     */
    private static final short size = 28;

    /**
     * Read and parse data from MNIST file, then save it to SimpleRowData object.
     * @param dataUrl absolute path for data file.
     * @param labelUrl absolute path for labels file.
     * @param type type of output data.<br>
     *             You can use:<br>
     *             <ul>
     *                  <li>{@link pl.sudokusolver.recognizerlib.data.DataType#Simple}</li>
     *                  <li>{@link pl.sudokusolver.recognizerlib.data.DataType#SimpleSVM}</li>
     *                  <li>{@link pl.sudokusolver.recognizerlib.data.DataType#Complex}</li>
     *             </ul>
     * @return data packed into IData (it is {@link pl.sudokusolver.recognizerlib.data.SimpleRowData}).
     * @throws VersionMismatchException if magic numbers from file didn't match.
     * @throws IOException if couldn't open file.
     * @throws IllegalArgumentException if type of data isn't supported.
     */
    public static IData read(String dataUrl, String labelUrl, DataType type)
            throws VersionMismatchException, IOException, IllegalArgumentException {
        ByteBuffer imgBuffer = loadFileToByteBuffer(dataUrl);
        ByteBuffer labelBuffer = loadFileToByteBuffer(labelUrl);

        int m1 = imgBuffer.getInt();
        int m2 = labelBuffer.getInt();
        if(m1 == 2049 && m2 == 2051)
            throw new VersionMismatchException("MNIST files don't have same version. Images have " + m1 + ", labels have "+m2+".");

        // todo: make bug report

        // images loading
        int iterations = imgBuffer.getInt();
        int imgNumber = iterations == 10000 ? iterations - 980 : iterations - 5923;
        int imgRows = imgBuffer.getInt();
        int imgCols = imgBuffer.getInt();

        // labels loading
        labelBuffer.getInt(); // iteams number

        // prepare matrix for data
        Mat trainData = Mat.zeros(imgNumber, size*size, CvType.CV_32FC1);
        Mat labels = createLabel(imgNumber, type);

        if(labels == null)
            throw new IllegalArgumentException("Invalid data type");

        // reading data from mnist files and saving to prepared matrix
        int curr = 0;
        for(int n = 0; n < iterations; n++) {
            int i = labelBuffer.get() & 0xFF;

            Mat img = loadImg(imgBuffer, imgRows, imgCols);
            if(i == 0) continue;

            putLabel(labels, i, curr, type);
            putImg(trainData, img, curr++);
        }
        return new SimpleRowData(trainData, labels, size);
    }


    /**
     * @param infile absolute path to file used to open.
     * @return all bytes read from this file.
     * @throws IOException if couldn't open file.
     */
    private static ByteBuffer loadFileToByteBuffer(String infile) throws IOException {
        RandomAccessFile f = new RandomAccessFile(infile, "r");
        FileChannel chan = f.getChannel();
        long fileSize = chan.size();
        ByteBuffer bb = ByteBuffer.allocate((int) fileSize);
        chan.read(bb);
        bb.flip();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < fileSize; i++)
            baos.write(bb.get());
        chan.close();
        f.close();
        return ByteBuffer.wrap(baos.toByteArray());
    }

    /**
     * @param size size of labels matrix.
     * @param type data type used to store labels.
     * @return matrix which contains only zeroes and have size of size x 1.
     */
    private static Mat createLabel(int size ,DataType type){
        switch (type){
            case Simple:
                return Mat.zeros(size, 1, CvType.CV_32FC1);
            case SimpleSVM:
                return  Mat.zeros(size, 1, CvType.CV_32SC1);
            case Complex:
                return Mat.zeros(size, 9, CvType.CV_32FC1);
            default:
                return null;
        }
    }

    /**
     * @param label value of label
     * @return string which represent this value.
     */
    private static String getStringFromLable(int label){
        switch (label) {
            case 9:
                return "000000001";
            case 8:
                return "000000010";
            case 7:
                return "000000100";
            case 6:
                return "000001000";
            case 5:
                return "000010000";
            case 4:
                return "000100000";
            case 3:
                return "001000000";
            case 2:
                return "010000000";
            case 1:
                return "100000000";
            default:
                return "000000000";
        }
    }

    /**
     * @param labels matrix whit all labels.
     * @param label value which we want to put to matrix.
     * @param n current label index.
     * @param type type of labels matrix.
     */
    private static void putLabel(Mat labels, int label, int n, DataType type){
        if(type == DataType.Complex) {
            String word = getStringFromLable(label);
            for (int k = 0; k < 9; k++) {
                double c = (word.charAt(k) == '1') ? 1 : 0;
                labels.put(n, k, c);
            }
        } else if(type == DataType.Simple || type == DataType.SimpleSVM){
            labels.put(n,0,label);
        }
    }

    /**
     * @param trainData matrix whit data.
     * @param img images which we want add to data.
     * @param n current data index.
     */
    private static void putImg(Mat trainData, Mat img, int n){
        Imgproc.resize(img, img, new Size(size,size));
        img = ImageProcessing.deskew(img,size);
        Mat procCell = ImageProcessing.procSimple(img, size);
        for (int k = 0; k < size * size; k++) {
            trainData.put(n, k, procCell.get(0, k));
        }
    }

    /**
     * @param source source of data
     * @param imgRows image rows
     * @param imgCols image cols
     * @return matrix of size <code>imgRows x imgCols</code> which contains <code>imgRows*imgCols</code> bytes from source.
     */
    private static Mat loadImg(ByteBuffer source, int imgRows, int imgCols){
        Mat img = Mat.zeros(imgRows,imgCols, CvType.CV_32FC1);

        for (int i = 0; i < imgRows; i++)
            for (int j = 0; j < imgCols; j++)
                img.put(i,j, source.get() & 0xFF);
        return img;
    }

    /**
     * You should't be able to create this class.
     */
    private MNISTReader(){}
}
