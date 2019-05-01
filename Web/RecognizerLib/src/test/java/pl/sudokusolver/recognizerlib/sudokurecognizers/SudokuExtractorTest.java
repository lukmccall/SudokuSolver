package pl.sudokusolver.recognizerlib.sudokurecognizers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.gridextractors.GridExtractor;

import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

@ExtendWith({_INIT_.class})
class SudokuExtractorTest {
    private boolean show = false;
    @Test
    void getCells(){
        try {
            List<String> images = _TestUtility_.getAllImages();
            for (String img : images.subList(0,1)) {
                GridExtractor gridExtractor = new GridExtractor(img);
                List<Mat> cells = SudokuExtractor.getCells(gridExtractor.getImg());
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