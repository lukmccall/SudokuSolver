package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineThicknessTest {

    @Test
    public void getValue() {
        LineThickness lineThickness = new LineThicknessStub();
        Assert.assertEquals(1.0,lineThickness.getValue(),0.0);
    }

    @Test
    public void setValue() {
        LineThickness lineThickness = new LineThicknessStub();
        try{
            lineThickness.setValue(0.8);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(0.8,lineThickness.getValue(),0.0);
        }
    }
}