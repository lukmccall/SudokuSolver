package pl.sudokusolver.recognizerlib.ocr;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.ocr.ml.ANN;
import pl.sudokusolver.recognizerlib.ocr.ml.SVM;
import pl.sudokusolver.recognizerlib.sudoku.Sudoku;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class RecognizerDumpManualTest {

    int all = 130;

    @Test
    @Ignore
    public void SVMfromDump() throws IOException, DigitExtractionFailedException {
        String data = "../../Data/Dump/Digit/";
        String save = "../../Data/Dump/Result/SVM/";
        SVM svm = new SVM(Utility.getSVMDump());
        File dir = new File(save);
        if(!dir.exists()) Assert.assertTrue("Couldn't create folder.",dir.mkdir());
        fromDump(data, save, svm);
    }

    @Test
    @Ignore
    public void ANNfromDump() throws IOException, DigitExtractionFailedException {
        String data = "../../Data/Dump/Digit/";
        String save = "../../Data/Dump/Result/ANN/";
        ANN ann = new ANN(Utility.getANNDump());
        File dir = new File(save);
        if(!dir.exists()) Assert.assertTrue("Couldn't create folder.",dir.mkdir());
        fromDump(data, save, ann);
    }

    private void fromDump(String data, String save, IRecognizer recognizer) throws IOException, DigitExtractionFailedException {
        for (int i = 0; i < all; i++) {
            Sudoku sudoku = new Sudoku();
            for(int j = 0; j < 81; j++){
                File file = new File(data+i+"/"+j+".jpg");
                if(file.exists()){
                    Mat img = imread(data+i+"/"+j+".jpg", -1);
                    sudoku.setDigit(recognizer.recognize(img).getFirst(), j / 9, j % 9);
                } else
                    sudoku.setDigit(0,j / 9, j % 9);

            }

            String pathToDat = "../../Data/TestImgs/"+i+".dat";

            Sudoku goodAnsSudoku = Sudoku.readFromDat(pathToDat);
            double score = goodAnsSudoku.score(sudoku);
            int scoreName = (int)(score*100);
            FileWriter fileWriter = new FileWriter(save+i+"_"+scoreName+".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.printf("Score %f\n", score);
            printWriter.println();

            for(int x = 0; x < 9; x++){
                for (int y = 0; y < 9; y++){
                    printWriter.printf("%d|%d ", goodAnsSudoku.getDigit(x,y), sudoku.getDigit(x,y));
                    if(y % 3 == 2 && y != 8)
                        printWriter.print(" |  ");
                }
                printWriter.println();
                if(x % 3 == 2 && x != 8) {
                    printWriter.println();
                    for (int k = 0; k < 9; k++)
                        printWriter.print("-----");
                    printWriter.println();
                    printWriter.println();
                }
            }
            printWriter.close();
            fileWriter.close();
        }
    }

}