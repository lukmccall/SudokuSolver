package pl.sudokusolver.recognizerlib.extractors.grid;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.filters.FixedWidthResizeFilter;
import pl.sudokusolver.recognizerlib.filters.SaveFilter;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class DefaultGridExtractStrategyManualTest {
    int all = 130;

    @Test
    @Ignore
    public void saveCuttedGrids() {
        GridExtractStrategy gridExtractStrategy = new DefaultGridExtractStrategy();
        String save = "../../Data/Dump/Grid/";
        for (int i = 0; i < all; i++) {

            String path = "../../Data/TestImgs/" + i + ".jpg";
            Mat img = imread(path, -1);
            new FixedWidthResizeFilter().apply(img);
            try {
                Mat grid = gridExtractStrategy.extract(img);
                System.out.println(grid.type());
                new SaveFilter(save + i + ".jpg").apply(grid);
            } catch (Exception e) {
                Assert.fail("Could't extract grid - " + e.getMessage());
            }
        }
    }
}