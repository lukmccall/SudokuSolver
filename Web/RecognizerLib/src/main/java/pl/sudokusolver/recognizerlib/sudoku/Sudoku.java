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
        System.out.println(toString());
    }

    @Override
    public String toString(){
        StringBuilder ss = new StringBuilder();
        for(int i = 0; i < 9; i++){
            ss.append("[");
            for (int j = 0; j < 8; j++)
                ss.append(sudoku[i][j]).append(", ");
            ss.append(sudoku[i][8]).append("]").append(System.getProperty("line.separator"));
        }
        return ss.toString();
    }
}
