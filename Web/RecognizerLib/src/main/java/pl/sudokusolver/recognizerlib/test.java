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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class test {

    private static boolean train = false;       //train SVM model

    public static void main(String[] args) throws NotFoundSudokuException, CellsExtractionFailedException {
        System.out.println("Rec Lib Test");

        Init.init("D:\\SyfPulpit\\ProjektIO\\opencv\\build\\java\\x64");

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


        BaseSudokuExtractor baseSudokuExtractor = new BaseSudokuExtractor(
                new DefaultGridExtractStrategy(new MedianBlur(3,11,2)),
                new SizeCellsExtractStrategy(),
                new FastDigitExtractStrategy(),
                svm,
                Collections.singletonList(new MaxResizeFilter()),
                Collections.singletonList(new CleanLinesFilter(20, 150, 20,new MedianBlur(9,21, 5))),
                null
        );


        String path2 = "../../Data/"+12+".jpg";
        Mat img2 = imread(path2);

        System.out.println(path2);
        Sudoku testSudoku2 = baseSudokuExtractor.extract(img2);
        testSudoku2.printSudoku();
        img2 = imread(path2);
        new DisplayHelper().apply(img2,path2);

        for(int i = 30; i <100; i++)
        {

            String path = "../../Data/"+i+".jpg";
            Mat img = imread(path);

            System.out.println(path);
            Sudoku testSudoku = baseSudokuExtractor.extract(img);
            testSudoku.printSudoku();
            img = imread(path);
          new DisplayHelper().apply(img,path);
        }





    }
}
