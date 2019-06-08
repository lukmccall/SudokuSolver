package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlurSizeTest {

    @Test
    public void getValue() {
        BlurSize blurSize = new BlurSizeStub();
        Assert.assertEquals(3,blurSize.getValue());
    }

    @Test
    public void setValue() {
        BlurSize blurSize = new BlurSizeStub();
        try{
            blurSize.setValue(-1);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(-1, blurSize.getValue());
        }
    }
}