package pl.sudokusolver.sudokurecognizerlib.gridrecognizers;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import pl.sudokusolver.sudokurecognizerlib.digitsrecognizers.IRecognizer;

public class SudokuDetector {
    private IRecognizer recognizer;

    public static boolean CONTAIN_DIGIT_SUB_MATRIX_DENSITY (Mat input) {
        double tl = input.size().height/3;
        double br = input.size().width - input.size().width/3;

        Rect cut = new Rect(new Point(tl,tl), new Point(br,br));

        return Core.countNonZero(new Mat(input, cut)) > 20;
    }
}
