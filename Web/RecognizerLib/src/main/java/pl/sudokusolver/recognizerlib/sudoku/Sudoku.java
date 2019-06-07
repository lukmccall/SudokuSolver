package pl.sudokusolver.recognizerlib.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class represents sudoku.
 */
public class Sudoku {
    /**
     * sudoku's grid.
     */
    private int grid[][];

    /**
     * Create object witch contains given grid.
     * @param grid new grid.
     */
    public Sudoku(int grid[][]){
        this.grid = grid;
    }

    /**
     * Create object with empty 9 x 9 grid.
     */
    public Sudoku(){
        grid = new int[9][9];
    }

    /**
     * @param x row
     * @param y col
     * @return get digit from x row and y col.
     */
    public int getDigit(int x, int y) {
        return grid[x][y];
    }

    /**
     * Set x row, y col to given digit
     * @param digit digit
     * @param x row
     * @param y col
     */
    public void setDigit(int digit, int x, int y) {
        this.grid[x][y] = digit;
    }

    /**
     * @return return whole grid.
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * @return <code>true</code> when sudoku is empty, otherwise return <code>false</code>.
     */
    public boolean empty() {
        for(int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if(grid[i][j] != 0) return false;
        return true;
    }

    /**
     * @param url path to .dat file.
     * @return sudoku creted base on dat file.
     * @throws IOException if couldn't open file.
     */
    public static Sudoku readFromDat(String url) throws IOException {
        Sudoku sudoku = new Sudoku();
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '.') index++;
                    if ('0' <= line.charAt(i) && line.charAt(i) <= '9') {
                        sudoku.setDigit(Character.getNumericValue(line.charAt(i)), index / 9, index % 9);
                        index++;
                    }
                }
            }
        }
        return sudoku;
    }

    /**
     * @param outer other sudoku.
     * @return return percent of same numbers.
     */
    public double score(Sudoku outer){
        int same = 0;
        int numbers = 0;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                if(grid[i][j]!= -1) {
                    numbers++;
                    if (grid[i][j] == outer.grid[i][j]) {
                        same++;
                    }
                }
        return (double)same/numbers;
    }

    @Override
    public String toString(){
        StringBuilder ss = new StringBuilder();
        for(int i = 0; i < 9; i++){
            ss.append("[");
            for (int j = 0; j < 8; j++)
                ss.append(grid[i][j]).append(", ");
            ss.append(grid[i][8]).append("]").append(System.getProperty("line.separator"));
        }
        return ss.toString();
    }
}
