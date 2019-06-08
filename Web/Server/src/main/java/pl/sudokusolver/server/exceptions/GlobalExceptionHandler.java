package pl.sudokusolver.server.exceptions;

import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.sudokusolver.recognizerlib.exceptions.CellsExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.DigitExtractionFailedException;
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

import java.io.IOException;


/**
 * Global handler for all exceptions.<br>
 * This class dispatching all exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler{
    /**
     * logger.
     */
    @Autowired
    private Logger LOGGER;

    /**
     * Page not found.
     * @return error response.
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleNoHandlerFoundException() {
        return new ErrorResponse(ErrorCodes.PageNotFound,"Strona nie znaleziona");
    }

    /**
     * Missing arguments.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        return new ErrorResponse(ErrorCodes.MissingParameter, "Brakujący argument: " + exception.getParameterName());
    }

    /**
     * Missing arguments (multi parts parameters).
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMissingServletRequestPartException(MissingServletRequestPartException exception){
        return new ErrorResponse(ErrorCodes.MissingParameter, "Brakujący argument: " + exception.getRequestPartName());
    }

    /**
     * Invalid argument.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception){
        return new ErrorResponse(ErrorCodes.InvalidParameter, "Niepoprawny argument: " + exception.getMessage());
    }

    /**
     * File is corrupted.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleIOException(IOException exception){
        return new ErrorResponse(ErrorCodes.FileIsCorrupted, "Plik jest uszkodzony");
    }

    /**
     * Sudoku not found or grid not extracted properly.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({NotFoundSudokuException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleNotFoundSudokuException(NotFoundSudokuException exception){
        return new ErrorResponse(ErrorCodes.SudokuNotFound, exception.getMessage());
    }

    /**
     * Digits couldn't be extracted.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({DigitExtractionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleDigitExtractionFailedException(DigitExtractionFailedException exception){
        return new ErrorResponse(ErrorCodes.SudokuNotFound, exception.getMessage());
    }

    /**
     * Sudoku couldn't be solved.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({SolvingFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleSolvingFailedException(SolvingFailedException exception){
        return new ErrorResponse(ErrorCodes.SolverFailed, exception.getMessage());
    }

    /**
     * Cells couldn't be extracted.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({CellsExtractionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleCellsExtractionFailedException(CellsExtractionFailedException exception){
        return new ErrorResponse(ErrorCodes.CellsExtractionFailed, exception.getMessage());
    }

    /**
     * Invalid json.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({JsonSyntaxException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleJsonSyntaxException(JsonSyntaxException exception){
        return new ErrorResponse(ErrorCodes.InvalidParameter, "Niepoprawny json: " + exception.getMessage());
    }

    /**
     * Unknown error (by openCV).
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({org.opencv.core.CvException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse hadneleCvException(org.opencv.core.CvException exception){
        return new ErrorResponse(ErrorCodes.Unknown, "Nieznany błąd. Spowodowany przez openCV." );
    }

    /**
     * Unknown error.
     * @param exception caused
     * @return error response
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse hadneleException(Exception exception){
        LOGGER.error("Exception: " + exception.getClass().getCanonicalName());
        return new ErrorResponse(ErrorCodes.Unknown, "Nieznany błąd.");
    }


}
