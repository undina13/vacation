package com.undina.vacation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceMethodTest {

    VacationService vacationService = new VacationService();

    @Test
    void getAmountTest() {
        BigDecimal res = vacationService.getAmount(new BigDecimal("30000"), 5);
        Assertions.assertEquals(res, new BigDecimal("4453.92"));
    }

    @Test
    void getCountDaysWithoutHolidays(){
        LocalDate start = LocalDate.of(2023, 5, 10);
        LocalDate end = LocalDate.of(2023, 5, 15);
        int days = vacationService.getCountDays(start, end);
        Assertions.assertEquals(days, 5);
    }

    @Test
    void getCountDaysWithHolidays(){
        LocalDate start = LocalDate.of(2023, 5, 1);
        LocalDate end = LocalDate.of(2023, 5, 15);
        int days = vacationService.getCountDays(start, end);
        Assertions.assertEquals(days, 12);
    }
}
