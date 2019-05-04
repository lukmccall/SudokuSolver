package pl.sudokusolver.recognizerlib.sudoku;

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
