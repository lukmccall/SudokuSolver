package pl.sudokusolver.recognizerlib.sudokurecognizers;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.extractors.grid.GridExtractor;

import java.util.List;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.resize;


@ExtendWith({_INIT_.class})
class GridExtractorTest {
    private static boolean showIMG = false;

    void getSudoku(String url) throws NotFoundSudokuExceptions {
        GridExtractor gridExtractor = new GridExtractor(url);
        if (showIMG) {
            resize(gridExtractor.getImg(), gridExtractor.getImg(), new Size(600, 600));
            imshow(url, gridExtractor.getImg());
            waitKey();
        }

    }

    @Test
    void getSudokuImg() {
        try {
            List<String> images = _TestUtility_.getAllImages();
            for (String img : images) {
                getSudoku(img);
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}