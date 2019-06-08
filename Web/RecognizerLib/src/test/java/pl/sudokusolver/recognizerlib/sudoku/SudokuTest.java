package pl.sudokusolver.recognizerlib.sudoku;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.Size;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib.filters.ResizeFilter;
import pl.sudokusolver.recognizerlib.utility.staticmethods.Utility;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({_INIT_.class})
public class SudokuTest {
    @Test
    void sudokuOnCreateHaveEmptyGridTest() {
        Sudoku sudoku = new Sudoku();
        for (short x = 0; x < 9; x++)
            for (short y = 0; y < 9; y++)
                assertEquals(0, sudoku.getDigit(x, y));

        Assert.assertTrue(sudoku.empty());
    }


    @Test
    void readFromDatTest() throws IOException {
        int grid[][] = {{4, 3, 7, 0, 6, 8, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 8, 0, 7},
        {0, 8, 0, 0, 0, 5, 0, 6, 0},
        {0, 4, 0, 0, 0, 1, 0, 0, 0},
        {8, 0, 3, 0, 5, 0, 6, 0, 9},
        {0, 0, 0, 6, 0, 0, 0, 3, 0},
        {0, 1, 0, 5, 0, 0, 0, 9, 0},
        {7, 0, 5, 0, 0, 6, 0, 0, 0},
        {0, 0, 0, 9, 8, 0, 1, 5, 6}};

        String path = "../../Data/TestImgs/" + 1 + ".dat";
        Sudoku sudoku = Sudoku.readFromDat(path);
        Sudoku sudokuExpected = new Sudoku(grid);

        Assert.assertEquals(sudokuExpected.toString(),sudoku.toString());

        String path2 = "../../Data/TestImgs/" + 100 + ".dat";
        Sudoku sudoku2 = Sudoku.readFromDat(path2);
        int grid2[][] = {{0, 0, 0, 0, 1, 7, 0, 5, 0},
                {9, 0, 3, 0, 0, 5, 2, 0, 7},
                {0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 1, 6, 0, 0, 4, 0, 0, 2},
                {0, 0, 0, 8, 0, 1, 0, 0, 0},
                {8, 0, 0, 5, 0, 0, 6, 4, 0},
                {0, 0, 9, 0, 0, 0, 0, 0, 0},
                {7, 0, 2, 1, 0, 0, 8, 0, 9},
                {0, 5, 0, 2, 3, 0, 0, 0, 0}};

        Sudoku sudokuExpected2 = new Sudoku(grid2);
        Assert.assertEquals(sudokuExpected2.toString(),sudoku2.toString());

    }

    @Test
    void readFromDatExceptionTest() throws IOException {
        String path = "../../Data/TestImgs/" + -1 + ".dat";

        assertThrows(IOException.class, ()->{
           Sudoku.readFromDat(path);

        }, "Path is wrong");

        assertThrows(IOException.class, ()->{
            Sudoku.readFromDat("blablabla");

        }, "Path is wrong");

        assertThrows(NullPointerException.class, ()->{
            Sudoku.readFromDat(null);

        }, "Path cant be null");

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
