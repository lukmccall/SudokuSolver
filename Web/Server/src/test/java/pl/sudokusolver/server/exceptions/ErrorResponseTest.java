package pl.sudokusolver.server.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    ErrorResponse e = new ErrorResponse(0,"error 0 - uknown");
    @Test
    void getErrorMessageTrue() {
        assertEquals(e.getErrorMessage(),"error 0 - uknown");
    }

    @Test
    void setErrorMessageTrue() {
        e.setErrorMessage("testErrorMessage");
        assertEquals(e.getErrorMessage(),"testErrorMessage");
        e.setErrorMessage(null);
        assertEquals(e.getErrorMessage(),null);
    }

    @Test
    void getAndSetErrorCodeFalse() {
        assertEquals(e.getErrorCode(),0);
        e.setErrorCode(5);
        assertEquals(e.getErrorCode(),5);

    }
    @Test
    void getErrorMessageFalse() {
        assertNotEquals(e.getErrorMessage(),"false");
    }

    @Test
    void setErrorMessageFalse() {
        e.setErrorMessage("testErrorMessage");
        assertNotEquals(e.getErrorMessage(),"");
    }

    @Test
    void getandSetErrorCodeFalse() {
        assertNotEquals(e.getErrorCode(),9);
        e.setErrorCode(5);
        assertNotEquals(e.getErrorCode(),9);
    }

}