package pl.sudokusolver.recognizerlib.filters;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class CleanLinesFilterTest {
    int all = 130;

    @Test
    @Ignore
    public void clean(){
        String data = "../../Data/Dump/Grid/";
        CleanLinesFilter cleanLinesFilter = new CleanLinesFilter(50, 65, 5,new MedianBlur(3,31, 15));

        String save = "../../Data/Dump/CleanGrid/";
        for (int i = 0; i < all; i++) {
            String path = data + i + ".jpg";
            Mat img = imread(path, -1);
            new ToGrayFilter().apply(img);
            new ResizeFilter(new Size(600,600)).apply(img);
            try {
                cleanLinesFilter.apply(img);
                new SaveFilter(save + i + ".jpg").apply(img);
            } catch (Exception e) {
                Assert.fail("Could't extract grid - " + e.getMessage());
            }
        }

    }
}