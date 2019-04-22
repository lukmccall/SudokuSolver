package pl.sudokusolver.recognizerlib.sudoku;

public class Sudoku {
    private short sudoku[][];

    public Sudoku(){
        sudoku = new short[9][9];
    }

    public short getDigit(int x, int y) {
        return sudoku[x][y];
    }

    public void setDigit(short digit, int x, int y) {
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
