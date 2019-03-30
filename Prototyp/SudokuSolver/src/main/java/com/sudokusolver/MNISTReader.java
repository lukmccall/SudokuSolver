package com.sudokusolver;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.ANN_MLP;
import org.opencv.ml.Ml;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;


class TrainData{
    public Mat data;
    public Mat labels;
}

public class MNISTReader {
    static private String imgUrl = "images";
    static private String labelUrl = "labels";
    private static ANN_MLP ann_mlp;

    public static byte[] loadFile(String infile) {
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

    public static ByteBuffer loadFileToByteBuffer(String infile) {
        return ByteBuffer.wrap(loadFile(infile));
    }

    static {
        // takie cos co laduje takie drugie cos pobrane z https://opencv.org
        // stosunkowo wazne!!!
        // a i jeszcze trzeba dodac ^^ do java.library.path
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
    }

    public static TrainData getData(){
        ByteBuffer imgBuffer = loadFileToByteBuffer(imgUrl);
        ByteBuffer labelBuffer = loadFileToByteBuffer(labelUrl);

        /*********IMAGES LOAD**********/
        int magicNumber = imgBuffer.getInt();
        int imgNumber = imgBuffer.getInt();
        int imgRows = imgBuffer.getInt();
        int imgCols = imgBuffer.getInt();

        /**********LABELS LOAD************/
        labelBuffer.getInt(); // magic number
        labelBuffer.getInt(); // iteams number


        Mat trainData = Mat.zeros(imgNumber, Detector.SZ*Detector.SZ, CvType.CV_32FC1);
        Mat labels = Mat.zeros(imgNumber, 10, CvType.CV_32FC1);

        for(int n = 0; n < imgNumber; n++) {
            int label = labelBuffer.get() & 0xFF;

            String word = "";
            if(label == 9) word = "0000000001";
            if(label == 8) word = "0000000010";
            if(label == 7) word = "0000000100";
            if(label == 6) word = "0000001000";
            if(label == 5) word = "0000010000";
            if(label == 4) word = "0000100000";
            if(label == 3) word = "0001000000";
            if(label == 2) word = "0010000000";
            if(label == 1) word = "0100000000";
            if(label == 0) word = "1000000000";

            for(int k = 0; k < 10; k++){
                double c = (word.charAt(k) == '1') ? 1 : 0;
                labels.put(n, k, c);
            }

            Mat img = Mat.zeros(imgRows,imgCols, CvType.CV_32FC1);

            for (int i = 0; i < imgRows; i++)
                for (int j = 0; j < imgCols; j++)
                    img.put(i,j, imgBuffer.get() & 0xFF);


            Size sz = new Size(Detector.SZ,Detector.SZ);
            Imgproc.resize(img, img, sz );
            img = Detector.deskew(img);
            Mat procCell = Detector.procSimple(img);
            for (int k = 0; k < Detector.SZ * Detector.SZ; k++) {
                trainData.put(n, k, procCell.get(0, k));
            }
        }
        TrainData d = new TrainData();
        d.data = trainData;
        d.labels = labels;
        return d;
    }

    public static TrainData getKNNData(){
        ByteBuffer imgBuffer = loadFileToByteBuffer(imgUrl);
        ByteBuffer labelBuffer = loadFileToByteBuffer(labelUrl);

        /*********IMAGES LOAD**********/
        int magicNumber = imgBuffer.getInt();
        int imgNumber = imgBuffer.getInt();
        int imgRows = imgBuffer.getInt();
        int imgCols = imgBuffer.getInt();

        /**********LABELS LOAD************/
        labelBuffer.getInt(); // magic number
        labelBuffer.getInt(); // iteams number


        Mat trainData = Mat.zeros(imgNumber, Detector.SZ*Detector.SZ, CvType.CV_32FC1);
        Mat labels = Mat.zeros(imgNumber, 1, CvType.CV_32FC1);

        for(int n = 0; n < imgNumber; n++) {
            int label = labelBuffer.get() & 0xFF;

            labels.put(n,0,label);
            Mat img = Mat.zeros(imgRows,imgCols, CvType.CV_32FC1);

            for (int i = 0; i < imgRows; i++)
                for (int j = 0; j < imgCols; j++)
                    img.put(i,j, imgBuffer.get() & 0xFF);


            Size sz = new Size(Detector.SZ,Detector.SZ);
            Imgproc.resize(img, img, sz );
            img = Detector.deskew(img);
            Mat procCell = Detector.procSimple(img);
            for (int k = 0; k < Detector.SZ * Detector.SZ; k++) {
                trainData.put(n, k, procCell.get(0, k));
            }
        }
        TrainData d = new TrainData();
        d.data = trainData;
        d.labels = labels;
        return d;
    }

    public static void main(String[] args) {
        TrainData d = getData();
        ann_mlp = ANN_MLP.create();

        Mat layers = new Mat(1 , 3 , CV_32FC1);
        layers.put(0, 0, Detector.SZ*Detector.SZ);
        layers.put(0, 1, 20);
        layers.put(0, 2, 10);
        ann_mlp.setLayerSizes(layers);
        ann_mlp.setActivationFunction(ANN_MLP.SIGMOID_SYM);

        ann_mlp.train(d.data, Ml.ROW_SAMPLE, d.labels);
        ann_mlp.save("ann.xml");
    }


}
