package pl.sudokusolver.recognizerlib.cellsextractors;

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
import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
class LineCellsExtractStrategyTest {

    @Test
    void extractTest() throws IOException {

        String url = _TestUtility_.getAllImages().get(4);
        CellsExtractStrategy cellsExtractStrategy = new LineCellsExtractStrategy();

        List<Mat> cells = cellsExtractStrategy.getCells(imread(url, 1));
        Assert.assertEquals("Couldn't extract 81 cells from sudoku", 81, cells.size());

    }
}