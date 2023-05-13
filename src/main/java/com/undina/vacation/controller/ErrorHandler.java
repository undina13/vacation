package com.undina.vacation.controller;

import com.undina.vacation.dto.ErrorResponse;
import com.undina.vacation.exception.NotFoundDayStartOrCountDays;
import com.undina.vacation.exception.StartVacationAfterFinishException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundDayStartOrCountDays.class)
    public ErrorResponse handleNotFoundException(final Exception e) {
        return new ErrorResponse(
                String.format("\"%s\"", e.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StartVacationAfterFinishException.class)
    public ErrorResponse handleBadRequestException(final Exception e) {
        return new ErrorResponse(
                String.format("\"%s\"", e.getMessage())
        );
    }
}
