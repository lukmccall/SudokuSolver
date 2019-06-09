package pl.sudokusolver.server.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    ErrorResponse e = new ErrorResponse(0,"error 0 - uknown");
    @Test
    void getErrorMessage() {
        assertEquals("error 0 - uknown",e.getErrorMessage());
    }

    @Test
    void setErrorMessage() {
        e.setErrorMessage("testErrorMessage");
        assertEquals("testErrorMessage",e.getErrorMessage());
        e.setErrorMessage(null);
        assertEquals(null, e.getErrorMessage());

        assertNotEquals("false", e.getErrorMessage());

        e.setErrorMessage("testErrorMessage");
        assertNotEquals("",e.getErrorMessage());


    }

}