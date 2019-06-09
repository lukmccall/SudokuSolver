package pl.sudokusolver.recognizerlib.extractors.grid;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;
import pl.sudokusolver.recognizerlib.filters.ToGrayFilter;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static pl.sudokusolver.recognizerlib._TestUtility_.getPathToResource;

@ExtendWith({_INIT_.class})
class DefaultGridExtractStrategyTest {
    @Test
    void extractFromImg() throws NotFoundSudokuException {
        DefaultGridExtractStrategy dge = new DefaultGridExtractStrategy();

        Mat input = imread(getPathToResource("/gridtest/input0.jpg"), -1);
        Mat expectedOuput = imread(getPathToResource("/gridtest/output0.jpg"), -1);

        Mat output = dge.extract(input);

        new ToGrayFilter().apply(output);
        new ToGrayFilter().apply(expectedOuput);

        Assert.assertEquals("output should have same size as expected output.",expectedOuput.size(), output.size());

        for(int i = 0; i < output.rows(); i++)
            for (int j = 0; j < output.cols(); j++)
                Assert.assertEquals(expectedOuput.get(i,j)[0], output.get(i,j)[0], 4);

        input = imread(getPathToResource("/gridtest/input1.jpg"), -1);
        expectedOuput = imread(getPathToResource("/gridtest/output1.jpg"), -1);
        output = dge.extract(input);

        new ToGrayFilter().apply(output);
        new ToGrayFilter().apply(expectedOuput);

        Assert.assertEquals("output should have same size as expected output.",expectedOuput.size(), output.size());

        for(int i = 0; i < output.rows(); i++)
            for (int j = 0; j < output.cols(); j++)
                Assert.assertEquals(expectedOuput.get(i,j)[0], output.get(i,j)[0], 4);
    }

}