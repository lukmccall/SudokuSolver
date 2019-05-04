package pl.sudokusolver.recognizerlib.ocr;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;
import pl.sudokusolver.recognizerlib.data.LearnData;
import pl.sudokusolver.recognizerlib.filters.BlurFilter;
import pl.sudokusolver.recognizerlib.filters.DisplayHelper;
import pl.sudokusolver.recognizerlib.filters.ResizeFilter;
import pl.sudokusolver.recognizerlib.filters.ToGrayFilter;
import pl.sudokusolver.recognizerlib.utility.staticmethods.ImageProcessing;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.opencv.imgcodecs.Imgcodecs.IMREAD_UNCHANGED;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.rectangle;

@ExtendWith({_INIT_.class})
class IRecognizerTest {

    //todo: move this test
    @Test
    void loadTestData() throws IOException {
        final short size = 28;

        String path = _TestUtility_.getPathToResource("/learndata/");
        List<String> files = _TestUtility_.getResourceFiles("/learndata/");
        for (int i = 0; i < files.size(); i++)
            files.set(i, path+files.get(i));

        LearnData learnData = new LearnData(files);
        Assert.assertEquals(3410, learnData.getData().rows());
        Assert.assertEquals(learnData.getData().rows(), learnData.getLabels().rows());
    }


}