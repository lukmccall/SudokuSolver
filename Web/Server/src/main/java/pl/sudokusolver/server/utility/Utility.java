package pl.sudokusolver.server.utility;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;

public class Utility {
    public static Mat multipartFileToMat(MultipartFile file) throws IOException {
        InputStream stream = file.getInputStream();
        byte[] byteFromStream = new byte[stream.available()];
        stream.read(byteFromStream);

        Mat sudoku = Imgcodecs.imdecode(new MatOfByte(byteFromStream), IMREAD_UNCHANGED);
        stream.close();
        return sudoku;
    }

}
