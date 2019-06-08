package pl.sudokusolver.app.CustomViews.RadioButtons;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrictModeTest {

    @Test
    public void getValue() {
        StrictMode strictMode = new StrictModeStub();
        Assert.assertTrue(strictMode.getValue());
    }

    @Test
    public void setValue() {
        StrictMode strictMode = new StrictModeStub();
        try {
            strictMode.setValue(false);
            fail("Fail, should throw NullPointerException");
        }
        catch (NullPointerException n) {
            Assert.assertFalse(strictMode.getValue());
        }
    }
}