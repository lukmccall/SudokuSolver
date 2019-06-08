package pl.sudokusolver.app;

import pl.sudokusolver.app.Controls;
import pl.sudokusolver.app.CustomViews.Canvas;

public class CanvasStub extends Canvas {
    public int onValueInserted;
    public String move;

    public int getOnValueInserted() {
        return onValueInserted;
    }

    public String getMove(){
        return move;
    }

    @Override
    public void onValueInserted(int value) {
        this.onValueInserted = value;
    }

    @Override
    public void movePlayerUp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("movePlayerUp");
        move = stringBuilder.toString();
    }

    @Override
    public void movePlayerDown() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("movePlayerDown");
        move = stringBuilder.toString();
    }

    @Override
    public void movePlayerLeft() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("movePlayerLeft");
        move = stringBuilder.toString();
    }

    @Override
    public void movePlayerRight() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("movePlayerRight");
        move = stringBuilder.toString();
    }

}
