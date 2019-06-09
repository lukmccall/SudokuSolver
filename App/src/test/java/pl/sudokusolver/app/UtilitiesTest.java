package pl.sudokusolver.app;

import javafx.geometry.Rectangle2D;
import org.junit.Assert;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class UtilitiesTest {

    @Test
    public void isJavaValid() {
        Assert.assertTrue(Utilities.isJavaValid());
    }

    @Test
    public void isValid() {
        Assert.assertFalse(Utilities.isValid(Rectangle2D.EMPTY));
    }

    @Test
    public void getError() {
        Assert.assertNull(Utilities.getError(0));
        Assert.assertEquals("Nie wykryto odpowiedniej wersji JAVY.",Utilities.getError(1));
        Assert.assertEquals("Nieznany format pliku (tylko .jpg, .png).",Utilities.getError(2));
        Assert.assertEquals("Niewykryte poprawne sudoku na zdjęciu.",Utilities.getError(3));
        Assert.assertEquals("Sudoku nie posiada rozwiązania.",Utilities.getError(4));
        Assert.assertEquals("Brak połączenia z siecią.",Utilities.getError(5));
        Assert.assertEquals("Serwer nieaktywny.",Utilities.getError(6));
        Assert.assertEquals("Plik nie istnieje.",Utilities.getError(7));
        Assert.assertEquals("Nie można wpisać tej wartości w to pole.",Utilities.getError(8));
        Assert.assertEquals("Nie można wyciąć bez zaznaczenia.",Utilities.getError(9));
    }

    @Test
    public void getFileExtension() {
        try {
            File file = File.createTempFile("test", ".jpg");
            Assert.assertEquals("jpg",Utilities.getFileExtension(file));
            File fileTwo = File.createTempFile("test", "test");
            Assert.assertEquals("",Utilities.getFileExtension(fileTwo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}