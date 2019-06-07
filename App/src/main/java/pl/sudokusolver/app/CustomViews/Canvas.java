package pl.sudokusolver.app.CustomViews;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pl.sudokusolver.app.GameBoard;
import pl.sudokusolver.app.Scenes.StageError;
import pl.sudokusolver.app.Theme;
import pl.sudokusolver.app.Values;

/**
 * Canvas that is used for graphical sudoku visualization
 */
public class Canvas extends javafx.scene.canvas.Canvas {

    private double SIZE_REC = 50;
    private int MAX_COL = 8;
    private int MAX_ROW = 8;

    private double offset_y;
    private int playerCol, playerRow;

    private GameBoard gameboard;

    public Canvas(){
        gameboard = new GameBoard();

        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    /**
     * Function to get numbers input by player
     * @return 2d array of numbers that have already been input
     */
    public int[][] getInitial() throws IllegalArgumentException{
        for (int i = 0; i <= MAX_ROW; i++){
            for (int j = 0; j <= MAX_COL; j++){
                if (!isValid(gameboard.getInitial()[i][j], i, j)){
                    throw new IllegalArgumentException();
                }
            }
        }
        return gameboard.getInitial();
    }

    /**
     * Function to remove every digit from sudoku
     */
    public void clear(){
        gameboard.clear();
        update();
    }

    /**
     * Function to get how much canvas is offset form top
     * @return height offset of sudoku
     */
    public double getOffsetY(){
        return offset_y;
    }

    /**
     * Function that redraws the sudoku to allow user to see changes that happened
     */
    public void update(){
        draw();
    }

    /**
     * Function to update solution array and display it to user
     * @param array containing solved sudoku
     */
    public void modifySolution(int [][] array){
        gameboard.modifySolution(array);
        update();
    }

    /**
     * Function to update initial array and display it to user
     * @param array containing initial version of sudoku
     */
    public void modifyInitial(int [][] array){
        gameboard.modifyInitial(array);
        update();
    }

    /**
     * Function that calculates which field user clicked and shows it to user
     * @param mouseX horizontal position of mouse
     * @param mouseY vertical position of mouse
     */
    public void mouseClick(double mouseX, double mouseY){
        playerCol = (int) (mouseX / SIZE_REC);
        playerRow = (int) (mouseY / SIZE_REC);

        update();
    }

    /**
     * Function to check if the digit can be placed at the current spot
     * @param value value to be inserted to sudoku
     * @return  true if it can be, false otherwise
     */
    private boolean isValid(int value, int inRow, int inCol){
        if (value < 0 || value > 9) return false;
        if (value == 0) return true;
        int[][] sudoku = gameboard.getInitial();

        for (int i = 0; i <= MAX_COL; i++){
            if (i == inRow) continue;
            if (sudoku[i][inCol] == value) return false;
        }
        for (int i = 0; i <= MAX_ROW; i++){
            if (i == inCol) continue;
            if (sudoku[inRow][i] == value) return false;
        }


        int startRow = inRow - inRow % 3;
        int startCol = inCol - inCol % 3;
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                if (row + startRow == inRow && col + startCol == inCol) continue;
                if (sudoku[row + startRow][col + startCol] == value) return false;
            }
        }

        return true;
    }

    /**
     * Function that modifies initial array because of user input
     * @param value value inserted by user
     */
    public void onValueInserted(int value){
        if (!isValid(value, playerRow, playerCol)){
            new StageError(Values.E008);
            return;
        }

        gameboard.modifyInitial(value, playerRow, playerCol);
        update();
    }

    /**
     * Function that changes position of field focused by user to one up
     */
    public void movePlayerUp(){
        if (playerRow > 0) playerRow--;
        update();
    }

    /**
     * Function that changes position of field focused by user to one down
     */
    public void movePlayerDown(){
        if (playerRow < MAX_ROW) playerRow++;
        update();
    }

    /**
     * Function that changes position of field focused by user to one left
     */
    public void movePlayerLeft(){
        if (playerCol > 0) playerCol--;
        update();
    }

    /**
     * Function that changes position of field focused by user to one right
     */
    public void movePlayerRight(){
        if (playerCol < MAX_COL) playerCol++;
        update();
    }

    /**
     * Inherited function that tells if the canvas can be resized over time
     * @return true if it can be resized, false otherwise
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Inherited function that returns preferable width with given height
     * @param height    height of window
     * @return  preferable width of window
     */
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    /**
     * Inherited function that returns preferable height with given width
     * @param width    width of window
     * @return  preferable height of window
     */
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    /**
     * Function to draw sudoku and digits on canvas
     */
    private void draw() {
        GraphicsContext context = this.getGraphicsContext2D();

        //setting size of a single field
        SIZE_REC = getHeight() > getWidth() ? getWidth() / 9 : getHeight() / 9;

        //calculating offset of the sudoku so that it can be vertically in center of the window
        offset_y = (getHeight() - SIZE_REC * 9) / 2;

        //coloring the whole canvas appropriate to chosen color theme
        if (Values.THEME == Theme.LIGHT){
            context.setFill(Color.web("F1F0F0"));
        }
        else{
            context.setFill(Color.web("34495E"));
        }
        context.fillRect(0, 0, getWidth() * 8, getWidth() * 8);

        //drawing colored rectangles that represent fields
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {

                //calculating position of fields
                double position_y = row * SIZE_REC + 2 + offset_y;
                double position_x = col * SIZE_REC + 2;

                double width = SIZE_REC - 2 * 2;

                //choosing the color depending of theme
                if (Values.THEME == Theme.LIGHT){
                    context.setFill(Color.WHITE);
                }
                else{
                    context.setFill(Color.web("4F6F8F"));
                }
                context.fillRect(position_x, position_y, width, width);
                ///context.setFill(Color.BLACK);
                //context.fillRect(position_x, position_y + width, width, 4);
            }
        }

        for (int i = 0; i <= 9; i+=3){
            double position_y = i * SIZE_REC + 2 + offset_y;
            double position_x = i * SIZE_REC + 2;

            context.setFill(Color.BLACK);
            context.fillRect(0, position_y - 4, SIZE_REC * 9 + 4, 4);
            context.fillRect(position_x - 4, offset_y, 4, SIZE_REC * 9);

        }

        //drawing a red border to indicate which field is focused by player
        context.setStroke(Color.RED);
        context.setLineWidth(5);
        if (playerRow < 9 && playerRow >= 0)
            context.strokeRect(playerCol * SIZE_REC + 2, playerRow * SIZE_REC + 2 + offset_y, SIZE_REC - 2*2, SIZE_REC - 2*2);

        int[][] initial = gameboard.getInitial();
        int[][] solution = gameboard.getSolution();

        //drawing digits onto fields
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                double position_y = row * SIZE_REC + offset_y + SIZE_REC / 2 + SIZE_REC / 5;
                double position_x = col * SIZE_REC + SIZE_REC / 2 - SIZE_REC / 2 / 4;

                context.setFont(new Font(SIZE_REC / 2));

                //if the digit is entered by user or detected while image input use one color to draw it
                if(initial[row][col] != 0) {
                    context.setFill(Color.BLACK);

                    if (!isValid(initial[row][col], row, col)){
                        context.setFill(Color.RED);
                    }

                    context.fillText(initial[row][col] + "", position_x, position_y);
                }
                //otherwise use another color
                else if (solution[row][col] != 0){
                    context.setFill(Color.rgb(20,170,0));
                    context.fillText(solution[row][col] + "", position_x, position_y);
                }
            }
        }
    }

}
