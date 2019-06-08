package pl.sudokusolver.recognizerlib.extractors.cells;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.filters.SaveFilter;

import java.io.File;
import java.util.List;

import static org.opencv.imgcodecs.Imgcodecs.imread;

@ExtendWith({_INIT_.class})
public class SizeCellsExtractStrategyManualTest {
    int all = 130;
    @Test
    @Ignore
    public void extract(){
        String data = "../../Data/Dump/CleanGrid/";
        CellsExtractStrategy cellsExtractStrategy = new SizeCellsExtractStrategy();
        String save = "../../Data/Dump/Cell/";

        for (int i = 0; i < all; i++) {

            String path = data + i + ".jpg";
            Mat img = imread(path, -1);
            try {
                List<Mat> cells = cellsExtractStrategy.extract(img);
                Assert.assertEquals("Should extracted 81 cells",81, cells.size());
                File f = new File(save+i);
                if(!f.exists()) Assert.assertTrue("Path doesn't exist", f.mkdir());
                for(int j = 0; j < cells.size(); j++){
                    Mat cell = cells.get(j);
                    new SaveFilter(save + i +"/" + j + ".jpg").apply(cell);
                }
            } catch (Exception e) {
                Assert.fail("Could't extract grid - " + e.getMessage());
            }
        }

    }
}