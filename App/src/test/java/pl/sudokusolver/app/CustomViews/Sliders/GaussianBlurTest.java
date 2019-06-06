package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GaussianBlurTest {

    @Test
    public void getValue() {
        GaussianBlur gaussianBlur = new GaussianBlurStub();
        Assert.assertEquals(1.0,gaussianBlur.getValue(),0.0);
    }

    @Test
    public void setValue() {
        GaussianBlur gaussianBlur = new GaussianBlurStub();
        try{
            gaussianBlur.setValue(0.05);
        }
        catch (NullPointerException n){
            Assert.assertEquals(0.05, gaussianBlur.getValue(),0.0);
        }
    }
}