package pl.sudokusolver.recognizerlib.sudoku;

public class Sudoku {
    private int grid[][];

    public Sudoku(){
        grid = new int[9][9];
    }

    public int getDigit(int x, int y) {
        return grid[x][y];
    }

    public void setDigit(int digit, int x, int y) {
        this.grid[x][y] = digit;
    }

    public void printSudoku(){
        System.out.println(toString());
    }

    public int[][] getGrid() {
        return grid;
    }

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
