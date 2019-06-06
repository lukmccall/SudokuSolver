package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlurCTest {

    @Test
    public void getValue() {
        BlurC blurC = new BlurCStub();
        Assert.assertEquals(15,blurC.getValue());
    }

    @Test
    public void setValue() {
        BlurC blurC = new BlurCStub();
        try{
            blurC.setValue(0);
        }
        catch (NullPointerException n){
            Assert.assertEquals(0,blurC.getValue());
        }
    }
}