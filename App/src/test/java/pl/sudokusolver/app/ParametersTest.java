package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParametersTest {
    
    @Test
    public void set() {
        Parameters parameters = new Parameters();
        parameters.set(1, 2, 3, 4, 5, 6,
        "testScaling","testRecognition", true);

        Assert.assertEquals(1,parameters.getLineThreshold());
        Assert.assertEquals(2,parameters.getLineGap());
        Assert.assertEquals(3,parameters.getMinLineSize());
        Assert.assertEquals(4,parameters.getBlurSize());
        Assert.assertEquals(5,parameters.getBlurBlockSize());
        Assert.assertEquals(6,parameters.getBlurC());
        Assert.assertEquals("testScaling",parameters.getScaling());
        Assert.assertEquals("testRecognition",parameters.getRecognition());
        Assert.assertTrue(parameters.getStrictMode());
    }

    @Test
    public void set1() {

        Parameters parameters = new Parameters();
        parameters.set(9, 8, 7, 6, 5, 4,
                "ScalingTest2","RecognitionTest2", true);

        Parameters parametersTest = new Parameters();
        parametersTest.set(parameters);

        Assert.assertEquals(9,parametersTest.getLineThreshold());
        Assert.assertEquals(8,parametersTest.getLineGap());
        Assert.assertEquals(7,parametersTest.getMinLineSize());
        Assert.assertEquals(6,parametersTest.getBlurSize());
        Assert.assertEquals(5,parametersTest.getBlurBlockSize());
        Assert.assertEquals(4,parametersTest.getBlurC());
        Assert.assertEquals("ScalingTest2",parameters.getScaling());
        Assert.assertEquals("RecognitionTest2",parameters.getRecognition());
        Assert.assertTrue(parameters.getStrictMode());

    }

    @Test
    public void getLineThreshold() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(50,parameters.getLineThreshold());
    }

    @Test
    public void getLineGap() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(5,parameters.getLineGap());
    }

    @Test
    public void getMinLineSize() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(65,parameters.getMinLineSize());
    }

    @Test
    public void getBlurSize() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(3,parameters.getBlurSize());
    }

    @Test
    public void getBlurBlockSize() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(31,parameters.getBlurBlockSize());
    }

    @Test
    public void getBlurC() {
        Parameters parameters = new Parameters();
        Assert.assertEquals(15,parameters.getBlurC());
    }

    @Test
    public void getScaling() {
        Parameters parameters = new Parameters();
        Assert.assertEquals("FIXED WIDTH SCALING",parameters.getScaling());
    }

    @Test
    public void getRecognition() {
        Parameters parameters = new Parameters();
        Assert.assertEquals("SVM",parameters.getRecognition());
    }

    @Test
    public void getStrictMode() {
        Parameters parameters = new Parameters();
        Assert.assertFalse(parameters.getStrictMode());
    }
}