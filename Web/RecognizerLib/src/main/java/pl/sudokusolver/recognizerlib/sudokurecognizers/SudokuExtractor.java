package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.digitbox.IDigitBox;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractor;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SudokuExtractor {
    private IRecognizer recognizer;
    private IDigitBox digitBox;
    private CellsExtractStrategy cellsExtractStrategy;


    private static boolean containsDigit (Mat input) {
        double tl = input.size().height/3;
        double br = input.size().width - input.size().width/3;

        Rect cut = new Rect(new Point(tl,tl), new Point(br,br));

        return Core.countNonZero(new Mat(input, cut)) > 20;
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

    public SudokuExtractor(IRecognizer recognizer, IDigitBox digitBox){
        this.recognizer = recognizer;
        this.digitBox = digitBox;
        this.cellsExtractStrategy = new SizeCellsExtractStrategy();
    }

    public Sudoku getSudokuFromGrid(GridExtractor gridExtractor){
        Sudoku sudoku = new Sudoku();

        List<Mat> cells = cellsExtractStrategy.getCells(gridExtractor.getImg());

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
