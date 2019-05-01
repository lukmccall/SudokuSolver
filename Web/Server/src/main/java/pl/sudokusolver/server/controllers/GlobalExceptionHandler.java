package pl.sudokusolver.server.controllers;

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
import pl.sudokusolver.server.exceptions.ErrorCodes;
import pl.sudokusolver.server.exceptions.ErrorRsponse;


@ControllerAdvice
public class GlobalExceptionHandler{
    @Autowired
    private Logger LOGGER;

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorRsponse handleNoHandlerFoundException() {
        return new ErrorRsponse(ErrorCodes.NotFound,"Page not found");
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorRsponse handleMissingServletRequestParameterException(MissingServletRequestParameterException exception){
        return new ErrorRsponse(ErrorCodes.MissingParameter, "Missing argument: " + exception.getParameterName());
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorRsponse handleMissingServletRequestPartException(MissingServletRequestPartException exception){
        return new ErrorRsponse(ErrorCodes.MissingParameter, "Missing argument: " + exception.getRequestPartName());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorRsponse hadneleException(Exception ex){
        LOGGER.trace("Exception: " + ex.getClass().getCanonicalName());
        ex.printStackTrace();
        return new ErrorRsponse(ErrorCodes.Unknown, "Unknown error");
    }


}