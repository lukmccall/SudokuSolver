package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@ExtendWith({_INIT_.class})
class SudokuDetectorTest {
    private boolean show = false;
    @Test
    void getCells(){
        try {
            List<String> images = _TestUtility_.getAllImages();
            for (String img : images.subList(0,1)) {
                GridImg gridImg = new GridImg(img);
                List<Mat> cells = SudokuDetector.getCells(gridImg.getImg());
                if(show) {
                    for (Mat cell : cells) {
                        imshow("Cell", cell);
                        waitKey();
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}