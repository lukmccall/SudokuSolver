package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinLineSizeTest {

    @Test
    public void getValue() {
        MinLineSize minLineSize = new MinLineSizeStub();
        Assert.assertEquals(100,minLineSize.getValue());
    }

    @Test
    public void setValue() {
        MinLineSize minLineSize = new MinLineSizeStub();
        try{
            minLineSize.setValue(10);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(10,minLineSize.getValue());
        }
    }
}