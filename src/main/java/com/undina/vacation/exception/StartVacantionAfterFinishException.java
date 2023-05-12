package com.undina.vacation.exception;

public class StartVacantionAfterFinishException extends RuntimeException{
    public StartVacantionAfterFinishException(String message) {
        super(message);
    }
}
