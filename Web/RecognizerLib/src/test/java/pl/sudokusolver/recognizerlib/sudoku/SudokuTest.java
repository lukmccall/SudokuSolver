package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.sudokusolver.recognizerlib._INIT_;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({_INIT_.class})
public class SudokuTest {
    @Test
    void sudokuOnCreateHaveEmptyGrid() {
        Sudoku sudoku = new Sudoku();
        for (short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                assertEquals(0, sudoku.getDigit(x, y));

        Assert.assertTrue(sudoku.empty());
    }


    @Test
    void readFromDatTest(){
        String path = "../../Data/TestImgs/" + 1 + ".dat";
        Sudoku sudoku = new Sudoku();
        sudoku.readFromDat(path);
    }



    @Test
    void scoreTest() {

        Sudoku sudoku = new Sudoku();
        Sudoku sudoku1 = new Sudoku();
        for (short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++) {
                sudoku.setDigit((x + y) % 10, x, y);
                sudoku1.setDigit((x + y) % 10, x, y);
            }   // tworze dwa identyczne sudoku

        Assert.assertEquals(1, sudoku.score(sudoku1), 1E-5);

        //zmieniam 9 wartości w sudoku1
        for (short x = 0; x < 9; x++) sudoku1.setDigit(10, 0, x);
        //teraz oczekiwana wartosc 0.888
        Assert.assertEquals(0.8888888889, sudoku.score(sudoku1), 1E-5);


        for (short x = 1; x < 9; x++) {
            for (short y = 0; y < 9; y++) {
                sudoku.setDigit(11, x, y);
            }
        }
        Assert.assertEquals(0.0, sudoku.score(sudoku1), 1E-5);

        //zostawiam 9 takich samych wartosci w sudoku1 i sudoku 2
        //oczekiwana wartosc 0.1111111
        for (short x = 0; x < 9; x++) sudoku.setDigit(10, 0, x);
        Assert.assertEquals(0.11111111111, sudoku.score(sudoku1), 1E-5);

    }

    @Test
    void toStringTest() {

        Sudoku sudoku = new Sudoku();
        for (short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                    sudoku.getDigit(x, y);

        StringBuilder grid1 = new StringBuilder();
        grid1.append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"))
                .append("[0, 0, 0, 0, 0, 0, 0, 0, 0]").append(System.getProperty("line.separator"));

        assertEquals(grid1.toString(), sudoku.toString());


    }

}
