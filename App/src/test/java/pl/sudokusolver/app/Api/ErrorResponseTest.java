package pl.sudokusolver.app.Api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void toString1() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = 0;
        errorResponse.errorCode = 0;
        errorResponse.errorMessage="TEST";
        String s = "0 0 TEST";
        Assert.assertEquals(s,errorResponse);
    }
}