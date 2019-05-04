package pl.sudokusolver.recognizerlib;

import com.google.common.collect.Lists;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.filters.*;

import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class test {

    public static void main(String[] args) throws NotFoundSudokuException {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        Mat img = imread("C:\\Users\\LukMcCall\\Studia\\IO\\SudokuSolver\\Web\\RecognizerLib\\9_data.jpg",1);
        Mat source = img.clone();
        new ToGrayFilter().apply(img);
        new BlurFilter().apply(img);

        List<MatOfPoint> countours = Lists.newArrayList();

        findContours(img, countours, new Mat(), RETR_TREE , CHAIN_APPROX_SIMPLE);
        System.out.println(countours.size());
        for (int i = 0; i < countours.size(); i++) {
            Rect boundingRect = boundingRect(countours.get(i));
            if(boundingRect.height > 28 && boundingRect.width < 60 && boundingRect.height < 50)
                rectangle(source,boundingRect,new Scalar(0,0,255));
//            drawContours(source, countours, i, new Scalar(0,0,255), -1);
        }
        new ResizeFilter(new Size(600,1000)).apply(source);
        new DisplayHelper().apply(source);
//        IRecognizer ann = new ANN("RecognizerLib/ann.xml");

//        BaseSudokuExtractor sudokuExtractor = new BaseSudokuExtractor(
//                new DefaultGridExtractStrategy(),
//                new LineCellsExtractStrategy(),
//                new ContoursDigitExtractStrategy(),
//                new PlaceTester(),
//                null,
//                Arrays.asList( new ToGrayFilter(),new BlurFilter() , new DisplayHelper()),
//                null
//
//        );
//        sudokuExtractor.extract("../Data/sudoku2.jpg").printSudoku();

    }
}
