package pl.sudokusolver.recognizerlib.extractors.cells;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class SizeCellsExtractStrategyTest {

    @Test
    void cellExtractionsShouldFailedTest(){
        assertThrows(CellsExtractionFailedException.class, ()->
                new SizeCellsExtractStrategy().extract(new Mat(1,1, CV_8UC1)),
        "Matrix is too small for this method."
        );
        assertThrows(CellsExtractionFailedException.class, ()->
                        new SizeCellsExtractStrategy().extract(new Mat(8,8, CV_8UC1)),
                "Matrix is too small for this method."
        );
        assertThrows(CellsExtractionFailedException.class, ()->
                        new SizeCellsExtractStrategy().extract(new Mat(8,9, CV_8UC1)),
                "Matrix is too small for this method."
        );
    }

    @Test
    void cellExtractionsTest() throws CellsExtractionFailedException {
        String allOnes = "[  1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1;\n" +
                "   1,   1,   1,   1,   1,   1,   1,   1,   1,   1]";

        Mat mat = Mat.ones(90, 90, CV_8UC1);
        List<Mat> l =new SizeCellsExtractStrategy().extract(mat);

        Assert.assertEquals(81, l.size());
        l.forEach(m->{
            Assert.assertEquals(10, m.rows());
            Assert.assertEquals(10, m.cols());
            Assert.assertEquals(allOnes, m.dump());
        });

    }
}