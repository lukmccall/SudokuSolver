package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlurBlockSizeTest {
    @Test
    public void getValue() {
        BlurBlockSize blurBlockSize = new BlurBlockSizeStub();
        Assert.assertEquals(31,blurBlockSize.getValue());
    }

    @Test
    public void setValue() {
        BlurBlockSize blurBlockSize = new BlurBlockSizeStub();
        try {
            blurBlockSize.setValue(10);
        }
        catch(NullPointerException n){
            Assert.assertEquals(10,blurBlockSize.getValue());
        }

    }
}