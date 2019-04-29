package pl.sudokusolver.recognizerlib.sudokurecognizers;

import com.google.common.collect.Lists;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.digitbox.IDigitBox;
import pl.sudokusolver.recognizerlib.ml.IRecognizer;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SudokuDetector {
    private IRecognizer recognizer;
    private IDigitBox digitBox;

    private static boolean containsDigit (Mat input) {
        double tl = input.size().height/3;
        double br = input.size().width - input.size().width/3;

        Rect cut = new Rect(new Point(tl,tl), new Point(br,br));

        return Core.countNonZero(new Mat(input, cut)) > 20;
    }

    public static List<Mat> getCells(Mat m) {
        int size = m.height() / 9;

        Size cellSize = new Size(size, size);
        List<Mat> cells = Lists.newArrayList();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Rect rect = new Rect(new Point(col * size, row * size), cellSize);

                Mat digit = new Mat(m, rect).clone();
//                removeNoise(digit);
                cells.add(digit);
            }
        }
        return cells;
    }

    private static void removeNoise(Mat submat) {
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat tmp = new Mat(submat.size(), submat.type());
        submat.copyTo(tmp);

        Imgproc.findContours(tmp, contours, new Mat(), Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
            Rect r = Imgproc.boundingRect(contours.get(i));
            if (isNoise(r.width, r.height, submat.cols(), submat.rows())) {
                Imgproc.drawContours(submat, contours, i, new Scalar(0), Core.FILLED);
            }
        }

    }

    private static  boolean isNoise(int width, int height, int tileWidth, int tileHeight) {
        if (width < tileWidth / 10 || height < tileHeight / 10) {
            return true;
        }
        return false;
    }

    public SudokuDetector(IRecognizer recognizer, IDigitBox digitBox){
        this.recognizer = recognizer;
        this.digitBox = digitBox;
    }

    public Sudoku getSudokuFromGrid(GridImg gridImg){
        Sudoku sudoku = new Sudoku();

        List<Mat> cells = getCells(gridImg.getImg());

        for(int i = 0; i < cells.size(); i++){
            Mat cell = cells.get(i);
            Optional<Rect> box = digitBox.getDigitBox(cell);
            if (box.isPresent() && containsDigit(cell)){
                Rect rect = box.get();

                Mat cutted = new Mat(cell, rect);
                sudoku.setDigit(recognizer.detect(cutted), i/9, i%9);
            }
        }

        return sudoku;
    }
}
