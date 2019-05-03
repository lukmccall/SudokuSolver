package pl.sudokusolver.recognizerlib.cellsextractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.extractors.digits.ContoursDigitExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.digits.DigitsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.CellsExtractStrategy;
import pl.sudokusolver.recognizerlib.extractors.cells.LineCellsExtractStrategy;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;

@ExtendWith({_INIT_.class})
class LineCellsExtractStrategyTest {

    @Test
    void extractTest() throws IOException {
        String url = _TestUtility_.getAllImages().get(4);
        CellsExtractStrategy cellsExtractStrategy = new LineCellsExtractStrategy();

        Mat img = imread(url, 1);
        cvtColor(img, img, COLOR_BGR2GRAY);

        List<Mat> cells = cellsExtractStrategy.extract(img);
        Assert.assertEquals("Couldn't extract 81 cells from sudoku", 81, cells.size());

        DigitsExtractStrategy digitsExtractStrategy = new ContoursDigitExtractStrategy();
        int pressent = 0;
        int index = 0;
        for (Mat m : cells)
//        Mat m = cells.get(13);
        {
//            System.out.println("Cell " + m.size() + " " + ++index);
            Optional<Mat> cell = digitsExtractStrategy.extractDigit(m);
            if(cell.isPresent())
            {
                pressent++;
//                imshow("Cell", cell.get());
//                waitKey();

            }
        }

        Assert.assertEquals("Couldn't extract all digits", 30, pressent);

    }
}