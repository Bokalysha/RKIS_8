package ru.bokalysha.rkis.Prac8.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bokalysha.rkis.Prac8.exceptions.create.ModelNotCreatedException;
import ru.bokalysha.rkis.Prac8.exceptions.delete.ModelNotDeletedException;
import ru.bokalysha.rkis.Prac8.exceptions.find.ModelNotFoundException;
import ru.bokalysha.rkis.Prac8.exceptions.update.ModelNotUpdatedException;
import ru.bokalysha.rkis.Prac8.exceptions.responses.ErrorResponse;


public class ErrorHandler<C extends ModelNotCreatedException, R extends ModelNotFoundException,
        U extends ModelNotUpdatedException, D extends ModelNotDeletedException> {

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleCreateException(C e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleReadException(R e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleUpdateException(U e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleDeleteException(D e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
