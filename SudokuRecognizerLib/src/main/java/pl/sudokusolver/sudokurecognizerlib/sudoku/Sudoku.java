package pl.sudokusolver.sudokurecognizerlib.sudoku;

public class Sudoku {
    private short sudoku[][];

    public Sudoku(){
        sudoku = new short[9][9];
    }

    public short getDigit(short x, short y) {
        return sudoku[x][y];
    }

    public void setDigit(short digit, short x, short y) {
        this.sudoku[x][y] = digit;
    }

    public void printSudoku(){
        for(int i = 0; i < 9; i++){
            System.out.print("[");
            for (int j = 0; j < 8; j++)
                System.out.print(sudoku[i][j]+", ");
            System.out.println(sudoku[i][8]+"]");
        }
    }
}
