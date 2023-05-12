package com.undina.vacation.service;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class HolidayDays {
    private final  List<LocalDate> holidays = Stream.of(
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 1, 2),
            LocalDate.of(2023, 1, 3),
            LocalDate.of(2023, 1, 4),
            LocalDate.of(2023, 1, 5),
            LocalDate.of(2023, 1, 6),
            LocalDate.of(2023, 1, 7),
            LocalDate.of(2023, 1, 8),
            LocalDate.of(2023, 2, 23),
            LocalDate.of(2023, 3, 8),
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 5, 9),
            LocalDate.of(2023, 6, 12),
            LocalDate.of(2023, 11, 4))
            .collect(Collectors.toList());

    public  static  List<LocalDate> getHolidays(){
        return holidays;
    }
}
