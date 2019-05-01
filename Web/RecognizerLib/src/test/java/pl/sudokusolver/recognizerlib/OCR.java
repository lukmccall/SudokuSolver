package pl.sudokusolver.recognizerlib;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.gridextractors.GridExtractor;
import pl.sudokusolver.recognizerlib.utility.Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@ExtendWith({_INIT_.class})
public class OCR {

    @Test
    void tesseract(){
        Tesseract tesseract = new Tesseract();

        List<String> images = null;
        GridExtractor gridExtractor = null;
        BufferedImage bf = null;
        try {
            images = _TestUtility_.getAllImages();
            gridExtractor = new GridExtractor(images.get(0));
            bf = Utility.matToBufferedImage(gridExtractor.getImg());
        } catch (IOException | NotFoundSudokuExceptions e) {
            e.printStackTrace();
        }


        imshow("cos", gridExtractor.getImg());
        waitKey();
        try {
            String url = _TestUtility_.getAllImages().get(2);
            tesseract.setTessVariable("tessedit_char_whitelist", "123456789");
            String text = tesseract.doOCR(bf);
            System.out.print(text);

        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
