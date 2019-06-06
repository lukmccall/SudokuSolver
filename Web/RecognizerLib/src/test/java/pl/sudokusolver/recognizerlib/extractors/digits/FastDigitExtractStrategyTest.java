package pl.sudokusolver.recognizerlib.extractors.digits;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.filters.ResizeFilter;
import pl.sudokusolver.recognizerlib.filters.SaveFilter;

import java.io.File;
import java.util.Optional;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class FastDigitExtractStrategyTest {
    int all = 130;

    @Test
    @Ignore
    public void extract(){
        String data = "../../Data/Dump/Cell/";
        FastDigitExtractStrategy fastDigitExtractStrategy = new FastDigitExtractStrategy();
        String save = "../../Data/Dump/Digit/";

        for (int i = 0; i < all; i++) {
            File f = new File(save+i);
            if(!f.exists()) Assert.assertTrue("Path doesn't exist", f.mkdir());
            for (int j = 0; j < 81; j++){
                String path = data + i + "/" + j + ".jpg";
                Mat img = imread(path, -1);
                new ResizeFilter(new Size(50f,50f)).apply(img);
                Optional<Mat> digit = fastDigitExtractStrategy.extractDigit(img);
                if(digit.isPresent())
                    new SaveFilter(save+i+"/"+j+".jpg").apply(digit.get());
            }

        }
    }
}