package pl.sudokusolver.app.CustomViews.Sliders;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceTest {

    @Test
    public void getValue() {
        Distance distance = new DistanceStub();
        Assert.assertEquals(1.0,distance.getValue(),0.0);
    }

    @Test
    public void setValue() {
        Distance distance = new DistanceStub();
        try{
            distance.setValue(0.05);
        }
        catch (NullPointerException n){
            Assert.assertEquals(0.05, distance.getValue(),0.0);
        }
    }
}