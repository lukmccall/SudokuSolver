package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineGapTest {

    @Test
    public void getValue() {
        LineGap lineGap = new LineGapStub();
        Assert.assertEquals(5,lineGap.getValue());
    }

    @Test
    public void setValue() {
        LineGap lineGap = new LineGapStub();
        try{
            lineGap.setValue(1);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(1,lineGap.getValue());
        }
    }
}