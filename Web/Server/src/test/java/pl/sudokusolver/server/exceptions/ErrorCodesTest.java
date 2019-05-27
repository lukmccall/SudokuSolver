package pl.sudokusolver.server.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodesTest {
    //prywatny konstruktor - tak chyba nie powinno byc nie da się tego użyć w ogóle
    @Test
    void getValueTest() {
        ErrorCodes a = new ErrorCodes(1);
        assertEquals(1,a.getValue());
    }

}