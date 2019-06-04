package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.Ignore;
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
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.ml.PlaceTester;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractWrapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
class BaseSudokuExtractorTest {

    /**
    *** Sudoku extractor with SVM ***
       ---    Correctness  ---

    All: 0.9806267806267808
    Full: 101
    Errors: 0
    Without errors: 0.9806267806267808

      ---    Performance  ---

    Avg time: 1003.6692307692308ms
    Min time: 448ms
    Max time: 1558ms

    ***********************************
    **/
    @Test
    @Ignore
    void testWithSVM(){
        IRecognizer svm = new SVM("../../Data/svm.xml");

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(svm)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 100, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with SVM ***");
        score(baseSudokuExtractor);
    }
    /**
    *** Sudoku extractor with ANN ***
       ---    Correctness  ---

    All: 0.9540360873694204
    Full: 19
    Errors: 0
    Without errors: 0.9540360873694204

       ---    Performance  ---

    Avg time: 901.7307692307693ms
    Min time: 369ms
    Max time: 1363ms

   **********************************
    **/
    @Test
    @Ignore
    void testWithANN(){
        IRecognizer ann = new ANN("../../Data/ann.xml");

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(ann)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 100, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with ANN ***");
        score(baseSudokuExtractor);
    }

    /**
     *** Sudoku extractor with tesseract ***
           ---    Correctness  ---

     All: 0.9637226970560305
     Full: 79
     Errors: 0
     Without errors: 0.9637226970560305

          ---    Performance  ---

     Avg time: 7320.523076923077ms
     Min time: 1304ms
     Max time: 12694ms

     *********************************
     **/
    @Test
    @Ignore
    void testWithTesseract(){
        IRecognizer tesseractWrapper = new TesseractWrapper();

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(tesseractWrapper)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 100, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with tesseract ***");
        score(baseSudokuExtractor);
    }

    private void score(SudokuExtractor extractor){
        int full = 0;
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
                testSudoku = extractor.extract(img,path);
            } catch (Exception e){
                expections++;
                System.out.println(e.getMessage());
                e.printStackTrace();
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