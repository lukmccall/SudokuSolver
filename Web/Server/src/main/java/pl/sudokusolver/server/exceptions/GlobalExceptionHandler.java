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
import pl.sudokusolver.recognizerlib.exceptions.NotFoundSudokuException;

import java.io.IOException;


@ControllerAdvice
public class GlobalExceptionHandler{
    @Autowired
    private Logger LOGGER;

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleNoHandlerFoundException() {
        return new ErrorResponse(ErrorCodes.PageNotFound,"Page not found");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        return new ErrorResponse(ErrorCodes.MissingParameter, "Missing argument: " + exception.getParameterName());
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMissingServletRequestPartException(MissingServletRequestPartException exception){
        return new ErrorResponse(ErrorCodes.MissingParameter, "Missing argument: " + exception.getRequestPartName());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception){
        return new ErrorResponse(ErrorCodes.InvalidParameter, "Invalid argument: " + exception.getMessage());
    }

    @ExceptionHandler({IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleIOException(IOException exception){
        return new ErrorResponse(ErrorCodes.FileIsCorrupted, "File is currupted");
    }

    @ExceptionHandler({NotFoundSudokuException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleNotFoundSudokuException(NotFoundSudokuException exception){
        return new ErrorResponse(ErrorCodes.SudokuNotFound, exception.getMessage());
    }

    @ExceptionHandler({SolvingFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleSolvingFailedException(SolvingFailedException exception){
        return new ErrorResponse(ErrorCodes.SolverFailed, exception.getMessage());
    }

    @ExceptionHandler({CellsExtractionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleCellsExtractionFailedException(CellsExtractionFailedException exception){
        return new ErrorResponse(ErrorCodes.CellsExtractionFailed, exception.getMessage());
    }

    @ExceptionHandler({JsonSyntaxException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleJsonSyntaxException(JsonSyntaxException exception){
        return new ErrorResponse(ErrorCodes.InvalidParameter, "Invalid json: " + exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse hadneleException(Exception ex){
        LOGGER.trace("Exception: " + ex.getClass().getCanonicalName());
//        ex.printStackTrace();
        return new ErrorResponse(ErrorCodes.Unknown, "Unknown error");
    }


}
