package pl.sudokusolver.recognizerlib;

import pl.sudokusolver.recognizerlib.data.DataType;
import pl.sudokusolver.recognizerlib.data.IData;
import pl.sudokusolver.recognizerlib.data.MNISTReader;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.ocr.ml.ILoader;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;

import java.io.IOException;

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



    }
}
