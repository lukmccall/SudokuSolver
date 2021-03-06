package pl.sudokusolver.recognizerlib;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({_INIT_.class})
class InitTest {
    @Test
    void checkMainResource(){
        Assert.assertNotNull("Resources should have svm file", Init.class.getResource("/svm.xml"));
        Assert.assertNotNull("Resources should have ann file", Init.class.getResource("/ann.xml"));
        Assert.assertNotNull("Resources should have tasseract file", Init.class.getResource("/tessdata/"));
        Assert.assertNotNull("Resources should have tasseract file", Init.class.getResource("/tessdata/eng.traineddata"));
     }
}