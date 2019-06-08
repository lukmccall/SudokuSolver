package pl.sudokusolver.recognizerlib.data;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.exceptions.VersionMismatchException;
import pl.sudokusolver.recognizerlib.filters.BlurFilter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sudokusolver.recognizerlib._TestUtility_.*;

@ExtendWith({_INIT_.class})
class MNISTReaderTest {

    @Test
    void mnistFilesDontExist() {
        assertThrows(IOException.class, () -> {
            MNISTReader.read("path_doesn't_exitst", "path_doesn't_exitst", DataType.Simple);
        },
        "Should throw when file doesn't exist.");
    }

    @Test
    void versionMismatchedException(){
        assertThrows(VersionMismatchException.class, () -> {
                MNISTReader.read(getPathToResource("/mnisttest/labels"), getPathToResource("/mnisttest/images"), DataType.Simple);
            });
    }

    @Test
    void readCorrectFiles() throws VersionMismatchException, IOException {
        IData testData = MNISTReader.read(
                getPathToResource("/mnisttest/images"),
                getPathToResource("/mnisttest/labels"), DataType.Simple
        );
        Assert.assertEquals("Data matrix is incorrect", 784, testData.getData().cols());
        Assert.assertEquals("Data matrix is incorrect", 9020, testData.getData().rows());
        Assert.assertEquals("Lables matrix is incorrect", 1, testData.getLabels().cols());
        Assert.assertEquals("Lables matrix is incorrect", 9020, testData.getLabels().rows());
    }
}