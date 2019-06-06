package pl.sudokusolver.app.CustomViews.RadioButtons;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScalingTest {

    @Test
    public void getValue() {
        Scaling scaling = new ScalingStub();
        Assert.assertEquals("Fixed Width Resize", scaling.getValue());
    }

    @Test
    public void setValue() {
        Scaling scaling = new ScalingStub();
        try {
            scaling.setValue("TEST");
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n) {
            Assert.assertEquals("TEST", scaling.getValue());
        }
    }
}