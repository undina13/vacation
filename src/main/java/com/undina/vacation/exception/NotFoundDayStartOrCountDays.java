package com.undina.vacation.exception;

public class NotFoundDayStartOrCountDays extends RuntimeException{
    public NotFoundDayStartOrCountDays(String message) {
        super(message);
    }
}
