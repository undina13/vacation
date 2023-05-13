package com.undina.vacation.exception;

public class StartVacationAfterFinishException extends RuntimeException{
    public StartVacationAfterFinishException(String message) {
        super(message);
    }
}
