package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {
    Parameters parameters = new Parameters();
    Parameters parametersTwo = new Parameters();
    @Test
    void set() {
        parameters.set(0.42,-1.872,1.32,1.54);

        Assert.assertEquals(0.42,parameters.getLineThickness(),0.0);
        Assert.assertEquals(-1.872,parameters.getProging(),0.0);
        Assert.assertEquals(1.32,parameters.getDistance(),0.0);
        Assert.assertEquals(1.54,parameters.getGauss(),0.0);

        Assert.assertEquals(1.0,parametersTwo.getLineThickness(),0.0);
        Assert.assertEquals(1.0,parametersTwo.getProging(),0.0);
        Assert.assertEquals(1.0,parametersTwo.getDistance(),0.0);
        Assert.assertEquals(1.0,parametersTwo.getGauss(),0.0);
    }

    @Test
    void set1(){
        parametersTwo.set(parameters);
        Assert.assertEquals(1.7,parameters.getLineThickness(),0.0);
        Assert.assertEquals(-2.8,parameters.getProging(),0.0);
        Assert.assertEquals(3.12,parameters.getDistance(),0.0);
        Assert.assertEquals(4.51,parameters.getGauss(),0.0);
    }

    @Test
    void getLineThickness() {
        Assert.assertEquals(1.7,parameters.getLineThickness(),0.0);
    }

    @Test
    void getProging() {
        Assert.assertEquals(-2.8,parameters.getProging(),0.0);
    }

    @Test
    void getDistance() {
        Assert.assertEquals(3.12,parameters.getDistance(),0.0);
    }

    @Test
    void getGauus(){
        Assert.assertEquals(4.51,parameters.getGauss(),0.0);
    }

    @BeforeEach
    void setUp() {
        parameters.set(1.7,-2.8,3.12,4.51);
    }


}