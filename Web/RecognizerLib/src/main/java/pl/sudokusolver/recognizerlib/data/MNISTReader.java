package pl.sudokusolver.recognizerlib.data;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MNISTReader {
    private static final short size = 28;

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

    private static Mat createLabel(int size ,DataType type){
        switch (type){
            case Simple:
                return Mat.zeros(size, 1, CvType.CV_32FC1);
            case SimpleSVM:
                return  Mat.zeros(size, 1, CvType.CV_32SC1);
            case Complex:
                return Mat.zeros(size, 10, CvType.CV_32FC1);
            default:
                return null;
        }
    }

    private static String getStringFromLable(int label){
        switch (label) {
            case 9:
                return "0000000001";
            case 8:
                return "0000000010";
            case 7:
                return "0000000100";
            case 6:
                return "0000001000";
            case 5:
                return "0000010000";
            case 4:
                return "0000100000";
            case 3:
                return "0001000000";
            case 2:
                return "0010000000";
            case 1:
                return "0100000000";
            case 0:
                return "1000000000";
            default:
                return "0000000000";
        }
    }

    private static void putLabel(Mat labels, int label, int n, DataType type){
        if(type == DataType.Complex) {
            String word = getStringFromLable(label);
            for (int k = 0; k < 10; k++) {
                double c = (word.charAt(k) == '1') ? 1 : 0;
                labels.put(n, k, c);
            }
        } else if(type == DataType.Simple || type == DataType.SimpleSVM){
            labels.put(n,0,label);
        }
    }

    private static void putImg(Mat trainData, Mat img, int n){
        Imgproc.resize(img, img, new Size(size,size));
        img = ImageProcessing.deskew(img,size);
        Mat procCell = ImageProcessing.procSimple(img, size);
        for (int k = 0; k < size * size; k++) {
            trainData.put(n, k, procCell.get(0, k));
        }
    }

    private static Mat loadImg(ByteBuffer from, int imgRows, int imgCols){
        Mat img = Mat.zeros(imgRows,imgCols, CvType.CV_32FC1);

        for (int i = 0; i < imgRows; i++)
            for (int j = 0; j < imgCols; j++)
                img.put(i,j, from.get() & 0xFF);
        return img;
    }

    public static IData read(String dataUrl, String labelUrl, DataType type)
            throws VersionMismatchException, IOException, IllegalArgumentException {
        ByteBuffer imgBuffer = loadFileToByteBuffer(dataUrl);
        ByteBuffer labelBuffer = loadFileToByteBuffer(labelUrl);

        if(imgBuffer.getInt() != labelBuffer.getInt())
            throw new VersionMismatchException("MNIST files don't have same version.");

        // IMAGES LOAD
        int imgNumber = imgBuffer.getInt();
        int imgRows = imgBuffer.getInt();
        int imgCols = imgBuffer.getInt();

        // LABELS LOAD
        labelBuffer.getInt(); // iteams number

        Mat trainData = Mat.zeros(imgNumber, size*size, CvType.CV_32FC1);
        Mat labels = createLabel(imgNumber, type);

        if(labels == null)
            throw new IllegalArgumentException("Invalid data type");

        for(int n = 0; n < imgNumber; n++) {
            putLabel(labels, labelBuffer.get() & 0xFF, n, type);

            Mat img = loadImg(imgBuffer, imgRows, imgCols);
            putImg(trainData, img, n);
        }
        return new SimpleRowData(trainData, labels, size);
    }
}
