package pl.sudokusolver.app;

import javafx.scene.input.KeyCode;
import pl.sudokusolver.app.CustomViews.Canvas;

/**
 * Controls what happen when user uses keyboard to interact with application
 */
public class Controls {

    private Canvas canvas;
    public Controls(Canvas canvas){
        this.canvas = canvas;
    }

    /**
     * Changes canvas depending on user input
     * @param keyCode user input
     */
    public void onKeyPressed(KeyCode keyCode){
        if (Singleton.getInstance().isBlocked()) return;
        //inserting into Sudoku
        if (keyCode == KeyCode.DIGIT1 || keyCode == KeyCode.NUMPAD1) {
            canvas.onValueInserted(1);
        }
        else if (keyCode == KeyCode.DIGIT2 || keyCode == KeyCode.NUMPAD2) {
            canvas.onValueInserted(2);
        }
        else if (keyCode == KeyCode.DIGIT3 || keyCode == KeyCode.NUMPAD3) {
            canvas.onValueInserted(3);
        }
        else if (keyCode == KeyCode.DIGIT4 || keyCode == KeyCode.NUMPAD4) {
            canvas.onValueInserted(4);
        }
        else if (keyCode == KeyCode.DIGIT5 || keyCode == KeyCode.NUMPAD5) {
            canvas.onValueInserted(5);
        }
        else if (keyCode == KeyCode.DIGIT6 || keyCode == KeyCode.NUMPAD6) {
            canvas.onValueInserted(6);
        }
        else if (keyCode == KeyCode.DIGIT7 || keyCode == KeyCode.NUMPAD7) {
            canvas.onValueInserted(7);
        }
        else if (keyCode == KeyCode.DIGIT8 || keyCode == KeyCode.NUMPAD8) {
            canvas.onValueInserted(8);
        }
        else if (keyCode == KeyCode.DIGIT9 || keyCode == KeyCode.NUMPAD9) {
            canvas.onValueInserted(9);
        }

        //removing from Sudoku
        else if (keyCode == KeyCode.BACK_SPACE || keyCode == KeyCode.DELETE || keyCode == KeyCode.ESCAPE){
            canvas.onValueInserted(0);
        }

        //moving between fields
        else if (keyCode == KeyCode.W || keyCode == KeyCode.UP){
            canvas.movePlayerUp();
        }
        else if (keyCode == KeyCode.A || keyCode == KeyCode.LEFT){
            canvas.movePlayerLeft();
        }
        else if (keyCode == KeyCode.S || keyCode == KeyCode.DOWN){
            canvas.movePlayerDown();
        }
        else if (keyCode == KeyCode.D || keyCode == KeyCode.RIGHT){
            canvas.movePlayerRight();
        }
    }
}
