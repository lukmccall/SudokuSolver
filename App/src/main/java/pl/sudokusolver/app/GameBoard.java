package pl.sudokusolver.app;

/**
 * Class containing all digits on sudoku and logic connected with them
 */
public class GameBoard {

    //array that will contain the complete solution to the board
    private int[][] solution;
    //array that will contain digits input by user or recognized from image
    private int[][] initial;


    public GameBoard() {
        //initial array full of 0
        initial = new int[9][9];

        //solution array full of 0
        solution = new int[9][9];
    }

    /***
     * Function to get digits input by user and recognized from picture
     * @return the initial filled-in numbers array
     */
    public int[][] getInitial() {
        return initial;
    }

    /***
     * Function to get digits returned by solver
     * @return the solved filled-in numbers array
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Function to remove every digit from input and solution arrays
     */
    public void clear(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                solution[i][j] = 0;
                initial[i][j] = 0;
            }
        }
    }

    /**
     * Function to modify solution array
     * @param array array containing solved sudoku
     */
    public void modifySolution(int[][] array){
        solution = array;
    }

    /**
     * Function to modify initial array
     * @param array array containing initial digits recognized from image
     */
    public void modifyInitial(int[][] array){
        clear();
        initial = array.clone();
    }

    /***
     * Function to modify only one digit in initial array
     * @param val the integer to insert in the initial array
     * @param row location in array x
     * @param col location in array y
     */
    public void modifyInitial(int val, int row, int col) {
        // only values from 0 to 9 inclusive are permitted
        if(val >= 0 && val <= 9) {
            initial[row][col] = val;
        }
    }

}