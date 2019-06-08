package pl.sudokusolver.app;

import com.sun.glass.ui.Robot;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.Assert;
import org.junit.Test;
import pl.sudokusolver.app.CustomViews.Canvas;

import static org.junit.Assert.*;

public class ControlsTest {

    @Test
    public void onKeyPressed_NumPad() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.NUMPAD1);
        Assert.assertEquals(1,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD2);
        Assert.assertEquals(2,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD3);
        Assert.assertEquals(3,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD4);
        Assert.assertEquals(4,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD5);
        Assert.assertEquals(5,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD6);
        Assert.assertEquals(6,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD7);
        Assert.assertEquals(7,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD8);
        Assert.assertEquals(8,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.NUMPAD9);
        Assert.assertEquals(9,canvas.getOnValueInserted());

    }

    @Test
    public void onKeyPressed_Digit() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.DIGIT1);
        Assert.assertEquals(1,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT2);
        Assert.assertEquals(2,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT3);
        Assert.assertEquals(3,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT4);
        Assert.assertEquals(4,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT5);
        Assert.assertEquals(5,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT6);
        Assert.assertEquals(6,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT7);
        Assert.assertEquals(7,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT8);
        Assert.assertEquals(8,canvas.getOnValueInserted());
        controls.onKeyPressed(KeyCode.DIGIT9);
        Assert.assertEquals(9,canvas.getOnValueInserted());

    }

    @Test
    public void onKeyPressed_BackSpace() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.BACK_SPACE);
        Assert.assertEquals(0,canvas.getOnValueInserted());

    }

    @Test
    public void onKeyPressed_Delete() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.DELETE);
        Assert.assertEquals(0,canvas.getOnValueInserted());

    }


    @Test
    public void onKeyPressed_Escape() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.ESCAPE);
        Assert.assertEquals(0,canvas.getOnValueInserted());

    }

    @Test
    public void onKeyPressed_Moving() {
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.UP);
        Assert.assertEquals("movePlayerUp",canvas.getMove());
        controls.onKeyPressed(KeyCode.DOWN);
        Assert.assertEquals("movePlayerDown",canvas.getMove());
        controls.onKeyPressed(KeyCode.LEFT);
        Assert.assertEquals("movePlayerLeft",canvas.getMove());
        controls.onKeyPressed(KeyCode.RIGHT);
        Assert.assertEquals("movePlayerRight",canvas.getMove());

    }

    @Test
    public void onKeyPressed_Moving_2(){
        CanvasStub canvas = new CanvasStub();
        Controls controls = new Controls(canvas);

        controls.onKeyPressed(KeyCode.W);
        Assert.assertEquals("movePlayerUp",canvas.getMove());
        controls.onKeyPressed(KeyCode.A);
        Assert.assertEquals("movePlayerLeft",canvas.getMove());
        controls.onKeyPressed(KeyCode.S);
        Assert.assertEquals("movePlayerDown",canvas.getMove());
        controls.onKeyPressed(KeyCode.D);
        Assert.assertEquals("movePlayerRight",canvas.getMove());

    }





}