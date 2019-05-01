package pl.sudokusolver.recognizerlib.sudokurecognizers;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuExceptions;
import pl.sudokusolver.recognizerlib.gridextractors.GridExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.opencv.core.Core.KMEANS_RANDOM_CENTERS;
import static org.opencv.core.Core.kmeans;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.*;

class CenterLinesComparator implements Comparator<Point> {
    @Override
    public int compare(Point t1, Point t2) {
        return Integer.compare((int) t1.x, (int) t2.y);
    }
}

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