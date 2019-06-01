package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
class BaseSudokuExtractorTest {

    @Test
    void procTest(){
        IRecognizer svm = new SVM("../../Data/svm.xml");

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy(new MedianBlur(3,21,2)))
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(svm)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 100, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        int full =0;
        double avgCorrectness = 0;
        int expections = 0;

        long avgTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = -1;

        int all = 130;
        for(int i = 0; i < all; i++){

            String path = "../../Data/TestImgs/"+i+".jpg";
            String pathToDat = "../../Data/TestImgs/"+i+".dat";
            Mat img = imread(path);
            Sudoku testSudoku = null;

            long startTime = System.currentTimeMillis();
            try{
                testSudoku = baseSudokuExtractor.extract(img,path);
            } catch (Exception e){
                expections++;
            }
            long endTime = System.currentTimeMillis();
            long currDuration = endTime - startTime;

            avgTime += currDuration;
            if(currDuration > maxTime) maxTime = currDuration;
            if(currDuration < minTime) minTime = currDuration;

            if(testSudoku != null) {
                Sudoku goodAnsSudoku = null;
                try {
                    goodAnsSudoku = Sudoku.readFromDat(pathToDat);
                } catch (IOException ignored) {}

                double s = goodAnsSudoku.score(testSudoku);

                if(s == 1.0f) full++;
                avgCorrectness += s;
            }
        }
        System.out.println();
        System.out.println("*** Sudoku extractor ***");
        System.out.println("---    Correctness  ---");
        System.out.println();
        System.out.println("All: " + (avgCorrectness/((double)all)));
        System.out.println("Full: " + full);
        System.out.println("Errors: " + expections);
        System.out.println("Without errors: " + (avgCorrectness/((double)all-(double)expections)));
        System.out.println();
        System.out.println("---    Performance  ---");
        System.out.println();
        System.out.println("Avg time: " + ((double)avgTime/((double)all)) +"ms");
        System.out.println("Min time: " + minTime+"ms");
        System.out.println("Max time: " + maxTime+"ms");
        System.out.println();
        System.out.println("**************************************");


    }

}