package pl.sudokusolver.recognizerlib;

import com.google.common.collect.Lists;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib.data.DataType;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.data.MNISTReader;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.extractors.cells.SizeCellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.FastDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.grid.DefaultGridExtractStrategy;
import pl.sudokusolver.recognizerlib.filters.*;
import pl.sudokusolver.recognizerlib.ocr.IRecognizer;
import pl.sudokusolver.recognizerlib.ocr.ml.ILoader;
import pl.sudokusolver.recognizerlib.ocr.ml.MLWrapper;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.sudoku.BaseSudokuExtractor;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.sudoku.SudokuExtractor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class test {

    private static boolean train = false;       //train SVM model

    public static void main(String[] args) throws NotFoundSudokuException, CellsExtractionFailedException {
        System.out.println("Rec Lib Test");

        Init.init("C:\\opencv4.0.1\\opencv\\build\\java\\x64");

        if(train)
        {
            ILoader ml;
            try {
                IData data = MNISTReader.read("..\\Data\\images","..\\Data\\labels", DataType.SimpleSVM);
                ml = new SVM(data);
                ml.dump("../Data/svm.xml");
            } catch (VersionMismatchException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        /*
        File folder = new File("../Data/temp/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {

                String name = file.getName();
                int id = Integer.parseInt(name.replaceAll(".jpg",""));

                Mat img = imread("../../Data/temp/"+name);

                String newFileName ="../../Data/more/"+good[id]+"_"+count[good[id]]+".jpg";
                count[good[id]]++;
                System.out.println(newFileName);
                imwrite( newFileName, img );
                System.out.println(good[id]);
            }
        }

*/

        IRecognizer svm = new SVM("../Data/svm.xml");

        SudokuExtractor baseSudokuExtractor = BaseSudokuExtractor.builder()
                                                    .setGridStrategy(new DefaultGridExtractStrategy(new MedianBlur(3,21,4)))
                                                    .setCellsStrategy(new SizeCellsExtractStrategy())
                                                    .setDigitsStrategy(new FastDigitExtractStrategy())
                                                    .setRecognizer(svm)
                                                    .addPreGridFilters(new ResizeFilter(new Size(1000,1000)))
                                                    .addPreCellsFilters(new CleanLinesFilter(20, 100, 20,new MedianBlur(3,21, 9)))
                                                    .build();
        double avg = 0;
        for(int i = 0; i < 100; i++){

            String path = "../Data/TestImgs/"+i+".jpg";
            String pathToDat = "../Data/TestImgs/"+i+".dat";
            Mat img = imread(path);
            System.out.println(path);
            Sudoku testSudoku = null;
            try{
                testSudoku = baseSudokuExtractor.extract(img);
            } catch (Exception e){

            }
            if(testSudoku != null) {
                Sudoku goodAnsSudoku = null;
                try {
                    goodAnsSudoku = Sudoku.readFromDat(pathToDat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                double s = goodAnsSudoku.score(testSudoku);
                System.out.println("Score " + s);
                avg += s;
            }
            else System.out.println("Score " + 0.0 + " <- cause by exception");
        }
        System.out.println("Avg: " + (avg/100.0));



    }
}
