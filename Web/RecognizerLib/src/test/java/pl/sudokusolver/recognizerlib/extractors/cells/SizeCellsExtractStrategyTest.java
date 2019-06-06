package pl.sudokusolver.recognizerlib.extractors.cells;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;

import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
class SizeCellsExtractStrategyTest {
    int all = 130;
    @Test
    @Ignore
    void extract(){
        String data = "../../Data/Dump/CleanGrid/";
        CellsExtractStrategy cellsExtractStrategy = new SizeCellsExtractStrategy();
        String save = "../../Data/Dump/Cells/";

        for (int i = 0; i < all; i++) {

            String path = data + i + ".jpg";
            Mat img = imread(path, -1);
            try {
                List<Mat> cells = cellsExtractStrategy.extract(img);
                //todo: make it
//                new SaveFilter(save + i + ".jpg").apply(grid);
            } catch (Exception e) {
                Assert.fail("Could't extract grid - " + e.getMessage());
            }
        }

    }
}