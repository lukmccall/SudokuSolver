package pl.sudokusolver.recognizerlib;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class InitTest {
    @Test
    void checkMainResource(){
        Assert.assertNotNull("Resources should have svm file", Init.class.getResource("/svm.xml"));
    }

}