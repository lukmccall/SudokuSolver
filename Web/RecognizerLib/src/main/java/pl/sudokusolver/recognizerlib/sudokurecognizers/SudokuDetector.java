package pl.sudokusolver.recognizerlib.sudokurecognizers;

import com.google.common.collect.Lists;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib.digitsrecognizers.IRecognizer;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;

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

    private static List<Mat> getCells(Mat m) {
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

    public SudokuDetector(IRecognizer recognizer, IDigitBox digitBox){
        this.recognizer = recognizer;
        this.digitBox = digitBox;
    }

    public Sudoku getSudokuFromGrid(Grid grid){
        Sudoku sudoku = new Sudoku();

        List<Mat> cells = getCells(grid.getSudokuGrid());

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
