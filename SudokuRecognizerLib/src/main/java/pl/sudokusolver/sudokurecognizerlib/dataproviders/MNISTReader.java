package pl.sudokusolver.sudokurecognizerlib.dataproviders;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.sudokurecognizerlib.imageprocessing.ImageProcessing;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MNISTReader {
    private static byte[] loadFile(String infile) {
        try {
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
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer loadFileToByteBuffer(String infile) {
        return ByteBuffer.wrap(loadFile(infile));
    }

    public static IData read(String dataUrl, String labelUrl, DataType type){
        final short size = 28;
        ByteBuffer imgBuffer = loadFileToByteBuffer(dataUrl);
        ByteBuffer labelBuffer = loadFileToByteBuffer(labelUrl);

        /*********IMAGES LOAD**********/
        int magicNumber = imgBuffer.getInt();
        int imgNumber = imgBuffer.getInt();
        int imgRows = imgBuffer.getInt();
        int imgCols = imgBuffer.getInt();

        /**********LABELS LOAD************/
        labelBuffer.getInt(); // magic number
        labelBuffer.getInt(); // iteams number


        Mat trainData = Mat.zeros(imgNumber, size*size, CvType.CV_32FC1);
        Mat labels = null;
        if(type == DataType.Complex) labels = Mat.zeros(imgNumber, 10, CvType.CV_32FC1);
        else if(type == DataType.Simple) labels = Mat.zeros(imgNumber, 1, CvType.CV_32FC1);
        for(int n = 0; n < imgNumber; n++) {
            int label = labelBuffer.get() & 0xFF;

            if(type == DataType.Complex) {
                String word = "";
                if (label == 9) word = "0000000001";
                if (label == 8) word = "0000000010";
                if (label == 7) word = "0000000100";
                if (label == 6) word = "0000001000";
                if (label == 5) word = "0000010000";
                if (label == 4) word = "0000100000";
                if (label == 3) word = "0001000000";
                if (label == 2) word = "0010000000";
                if (label == 1) word = "0100000000";
                if (label == 0) word = "1000000000";

                for (int k = 0; k < 10; k++) {
                    double c = (word.charAt(k) == '1') ? 1 : 0;
                    labels.put(n, k, c);
                }
            } else if(type == DataType.Simple){
                labels.put(n,0,label);
            }
            Mat img = Mat.zeros(imgRows,imgCols, CvType.CV_32FC1);

            for (int i = 0; i < imgRows; i++)
                for (int j = 0; j < imgCols; j++)
                    img.put(i,j, imgBuffer.get() & 0xFF);


            Size sz = new Size(size,size);
            Imgproc.resize(img, img, sz );
            img = ImageProcessing.deskew(img,size);
            Mat procCell = ImageProcessing.procSimple(img, size);
            for (int k = 0; k < size * size; k++) {
                trainData.put(n, k, procCell.get(0, k));
            }
        }

        return new RowData(trainData, labels, size);
    }
}
