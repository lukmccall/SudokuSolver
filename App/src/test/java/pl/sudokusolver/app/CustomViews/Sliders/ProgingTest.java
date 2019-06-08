package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProgingTest {

    @Test
    public void getValue() {
        Proging proging = new ProgingStub();
        Assert.assertEquals(1.0,proging.getValue(),0.0);
    }

    @Test
    public void setValue() {
        Proging proging = new ProgingStub();
        try{
            proging.setValue(1.5);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n){
            Assert.assertEquals(1.5,proging.getValue(),0.0);
        }
    }
}