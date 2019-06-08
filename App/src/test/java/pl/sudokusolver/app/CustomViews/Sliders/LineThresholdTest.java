package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineThresholdTest {

    @Test
    public void getValue() {
        LineThreshold lineThreshold = new LineThresholdStub();
        Assert.assertEquals(50,lineThreshold.getValue());
    }

    @Test
    public void setValue() {
        LineThreshold lineThreshold = new LineThresholdStub();
        try{
            lineThreshold.setValue(10);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(10,lineThreshold.getValue());
        }
    }
}