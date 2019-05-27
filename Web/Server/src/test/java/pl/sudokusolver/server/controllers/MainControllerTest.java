package pl.sudokusolver.server.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    @Test
    void homeTestTrue() {
        MainController a = new MainController();
        assertEquals("home",a.home());
    }
    @Test
    void homeTestFalse() {
        MainController a = new MainController();
        assertNotEquals("HOME",a.home());
        assertNotEquals("LOLOLO",a.home());
    }

}