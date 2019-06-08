package pl.sudokusolver.app.CustomViews.RadioButtons;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecognitionTest {

    @Test
    public void getValue() {
        Recognition recognition = new RecognitionStub();
        Assert.assertEquals("SVM", recognition.getValue());
    }

    @Test
    public void setValue() {
        Recognition recognition = new RecognitionStub();
        try {
            recognition.setValue("TEST");
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n) {
            Assert.assertEquals("TEST", recognition.getValue());
        }
    }
}