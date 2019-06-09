package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractSimple;
import pl.sudokusolver.recognizerlib.ocr.tesseract.TesseractStrictMode;

import java.io.File;
import java.io.IOException;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
class BaseSudokuExtractorManualTest {

    private int all = 156;

    /**
    *** Sudoku extractor with SVM ***
       ---    Correctness  ---

    All: 0.9754669199113641
    Full: 110
    Errors: 0
    Without errors: 0.9754669199113641

       ---    Performance  ---
    Avg time: 202.06410256410257ms
    Min time: 84ms
    Max time: 383ms

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
                .addPreGridFilters(new MaxResizeFilter(new Size(2500,2500)))
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 65, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("***  Sudoku extractor with SVM ***");
        score(baseSudokuExtractor);
    }
    /**
    *** Sudoku extractor with ANN ***
       ---    Correctness  ---

     All: 0.9502215891104777
     Full: 22
     Errors: 0
     Without errors: 0.9502215891104777

       ---    Performance  ---

     Avg time: 149.26282051282053ms
     Min time: 72ms
     Max time: 400ms

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
                .addPreCellsFilters(new CleanLinesFilter(50, 65, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with ANN ***");
        score(baseSudokuExtractor);
    }

    /**
     *** Sudoku extractor with tesseract ***
           ---    Correctness  ---

     All: 0.9603513770180439
     Full: 86
     Errors: 0
     Without errors: 0.9603513770180439

          ---    Performance  ---

     Avg time: 4131.294871794872ms
     Min time: 702ms
     Max time: 7705ms

     *********************************
     **/
    @Test
    @Ignore
    void testWithTesseractSimple(){
        IRecognizer tesseract = new TesseractSimple();

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(tesseract)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 65, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with tesseract simple ***");
        score(baseSudokuExtractor);
    }

    /**
     *** Sudoku extractor with tesseract stict ***
     ---    Correctness  ---

     All: 0.7211142766698321
     Full: 84
     Errors: 42
     Without errors: 0.9867879575481913

     ---    Performance  ---

     Avg time: 3545.7051282051284ms
     Min time: 240ms
     Max time: 6656ms

     **************************************
     */
    @Test
    @Ignore
    void testWithTesseractStrict(){
        IRecognizer tesseract = new TesseractStrictMode();

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                .setGridStrategy(new DefaultGridExtractStrategy())
                .setCellsStrategy(new SizeCellsExtractStrategy())
                .setDigitsStrategy(new FastDigitExtractStrategy())
                .setRecognizer(tesseract)
                .addPreGridFilters(new FixedWidthResizeFilter())
                .addPreCellsFilters(new ToGrayFilter())
                .addPreCellsFilters(new ResizeFilter(new Size(600,600)))
                .addPreCellsFilters(new CleanLinesFilter(50, 65, 5,new MedianBlur(3,31, 15)))
                .addPreDigitsFilters(new ResizeFilter(new Size(50f,50f)))
                .build();

        System.out.println("*** Sudoku extractor with tesseract stict ***");
        score(baseSudokuExtractor);
    }

    private void score(SudokuExtractor extractor){
        int full = 0;
        double avgCorrectness = 0;
        int expections = 0;

        long avgTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = -1;


        for(int i = 0; i < all; i++){
            String path;
            File n = new File("../../Data/TestImgs/"+i+".jpg");
            if(n.exists()) path = "../../Data/TestImgs/"+i+".jpg";
            else path = "../../Data/TestImgs/"+i+".png";

            String pathToDat = "../../Data/TestImgs/"+i+".dat";
            Mat img = imread(path);
            Sudoku testSudoku = null;

            long startTime = System.currentTimeMillis();
            try{
                testSudoku = extractor.extract(img);
            } catch (Exception e){
                expections++;
                e.printStackTrace();
                System.out.println(e.getMessage());
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
                else if(s < 0.7) System.out.println("Below 70%. Score " + s + " - " + i);
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