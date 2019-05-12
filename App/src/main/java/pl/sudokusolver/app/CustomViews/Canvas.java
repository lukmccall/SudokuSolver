package pl.sudokusolver.app.CustomViews;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pl.sudokusolver.app.GameBoard;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

public class Canvas extends javafx.scene.canvas.Canvas {


    public double SIZE_REC = 50;
    public GameBoard gameboard;
    public double offset_y;
    public int playerCol, playerRow;

    public Canvas(){
        gameboard = new GameBoard();

        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void update(){
        draw();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    /**
     * Method draws with data from the GameBoard instance of the Controller class
     */
    private void draw() {
        SIZE_REC = getHeight() / 9;
        if (SIZE_REC  * 9 > getWidth()){
            SIZE_REC = (getWidth()) / 9;
        }
        offset_y = (getHeight() - SIZE_REC * 9) / 2;

        GraphicsContext context = this.getGraphicsContext2D();

        if (Values.THEME == Theme.LIGHT){
            context.setFill(Color.web("F1F0F0"));
        }
        else{
            context.setFill(Color.web("34495E"));
        }

        context.fillRect(0, 0, getWidth() * 8, getWidth() * 8);

        // draw white rounded rectangles for our board
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                double position_y = row * SIZE_REC + 2 + offset_y;

                double position_x = col * SIZE_REC + 2;

                double width = SIZE_REC - 2 * 2;

                if (Values.THEME == Theme.LIGHT){
                    context.setFill(Color.WHITE);
                }
                else{
                    context.setFill(Color.web("4F6F8F"));
                }

                context.fillRect(position_x, position_y, width, width);
            }
        }

        context.setStroke(Color.RED);
        context.setLineWidth(5);
        if (playerRow < 9 && playerRow > 0)
            context.strokeRect(playerCol * SIZE_REC + 2, playerRow * SIZE_REC + 2 + offset_y, SIZE_REC - 2*2, SIZE_REC - 2*2);

        // draw the numbers from our GameBoard instance
        int[][] initial = gameboard.getInitial();
        int[][] player = gameboard.getPlayer();

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                double position_y = row * SIZE_REC + offset_y + SIZE_REC / 2 + SIZE_REC / 5;// + SIZE_REC / 2; //SIZE_REC / 2 / 2 + offset_y;

                double position_x = col * SIZE_REC + SIZE_REC / 2 - SIZE_REC / 2 / 4;

                if (initial[row][col] != 0){
                    context.setFill(Color.BLACK);
                }
                else if (player[row][col] != 0){
                    context.setFill(Color.RED);
                }


                context.setFont(new Font(SIZE_REC / 2));

                if(initial[row][col] != 0) {
                    context.fillText(initial[row][col] + "", position_x, position_y);
                }
                else if (player[row][col] != 0){
                    context.fillText(player[row][col] + "", position_x, position_y);
                }
            }
        }
    }

}