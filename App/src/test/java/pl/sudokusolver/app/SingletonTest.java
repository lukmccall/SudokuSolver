package pl.sudokusolver.app;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonTest {

    @Test
    public void isBlocked() {
        Singleton singleton = Singleton.getInstance();
        Assert.assertFalse(singleton.isBlocked());
    }

    @Test
    public void block() {
        Singleton singleton = Singleton.getInstance();
        singleton.block();
        Assert.assertTrue(singleton.isBlocked());
    }

    @Test
    public void unblock() {
        Singleton singleton = Singleton.getInstance();
        singleton.unblock();
        Assert.assertFalse(singleton.isBlocked());
    }
    @Test
    public void unblock_block() {
        Singleton singleton = Singleton.getInstance();
        singleton.block();
        Assert.assertTrue(singleton.isBlocked());
        singleton.unblock();
        Assert.assertFalse(singleton.isBlocked());
    }
}