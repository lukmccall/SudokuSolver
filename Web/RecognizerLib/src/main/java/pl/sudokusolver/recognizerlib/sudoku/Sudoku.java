package pl.sudokusolver.recognizerlib.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa zawierająca liczbową reprezentację sudoku
 */
public class Sudoku {
    /**
     * Siatka sudoku
     */
    private int grid[][];

    public Sudoku(int grid[][]){
        this.grid = grid;
    }

    public Sudoku(){
        grid = new int[9][9];
    }

    /**
     * @param x wiersz
     * @param y kolumna
     * @return liczbę znajdującą sie na miejscu (x,y)
     */
    public int getDigit(int x, int y) {
        return grid[x][y];
    }

    /**
     * @param digit liczba która znajdzie się na miejscu (x,y)
     * @param x wiersz
     * @param y kolumna
     */
    public void setDigit(int digit, int x, int y) {
        this.grid[x][y] = digit;
    }

    /**
     * Wyświetla sudoku na standartowe wyjście
     */
    public void printSudoku(){
        System.out.println(toString());
    }

    /**
     * @return tablicowa reprezentacja sudoku
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * @return <code>true</code> gdy sudoku jest puste, <code>false</code> gdy sudoku zawiera chodź jedną licznę różną od 0.
     */
    public boolean empty() {
        for(int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if(grid[i][j] != 0) return false;
        return true;
    }

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

    public double score(Sudoku outer){
        int same = 0;
        int numbers = 0;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                if(grid[i][j]!= 0) {
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
