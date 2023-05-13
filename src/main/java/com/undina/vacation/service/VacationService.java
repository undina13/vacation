package com.undina.vacation.service;

import com.undina.vacation.dto.VacationDto;
import com.undina.vacation.exception.NotFoundDayStartOrCountDays;
import com.undina.vacation.exception.StartVacationAfterFinishException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Predicate;

@Service
public class VacationService {
    private static final BigDecimal AVERAGE_COUNT_DAYS_IN_MONTH = BigDecimal.valueOf(29.3);
    private static final double NDFL = 0.13;

    /**
     * Из тз не совсем понятно, как должна работать программа с указанием дат отпуска, поэтому рассматриваю все возможные
     * случаи,  в реальной жизни уточнила бы этот вопрос до написания кода
     */
    public BigDecimal getAmountOfVacationPay(VacationDto vacationDto) {
        if (vacationDto.getDaysOfVacation() == null &&
                (vacationDto.getDayStart() == null || vacationDto.getDayEnd() == null)) {
            throw new NotFoundDayStartOrCountDays("Не указана дата начала отпуска/ окончания отпуска или количество " +
                    "дней отпуска");
        }
        Integer daysOfVacation = vacationDto.getDaysOfVacation();
        if (vacationDto.getDayStart() != null && vacationDto.getDayEnd() != null) {
            if (!vacationDto.getDayStart().isBefore(vacationDto.getDayEnd())) {
                throw new StartVacationAfterFinishException("Дата начала отпуска не может быть позже или равна дате " +
                        "выхода на работу");
            }
            daysOfVacation = getCountDays(vacationDto.getDayStart(), vacationDto.getDayEnd());
        } else if (vacationDto.getDaysOfVacation() != null && vacationDto.getDayStart() != null) {
            LocalDate dayEnd = vacationDto.getDayStart().plusDays(vacationDto.getDaysOfVacation());
            daysOfVacation = getCountDays(vacationDto.getDayStart(), dayEnd);
        } else if (vacationDto.getDaysOfVacation() != null && vacationDto.getDayEnd() != null) {
            LocalDate dayStart = vacationDto.getDayEnd().minusDays(vacationDto.getDaysOfVacation());
            daysOfVacation = getCountDays(dayStart, vacationDto.getDayEnd());
        }
        return getAmount(vacationDto.getAverageSalary(), daysOfVacation);
    }

    BigDecimal getAmount(BigDecimal averageSalary, int daysOfVacation) {
        BigDecimal sumWithNDFLForOneDay = averageSalary.divide(AVERAGE_COUNT_DAYS_IN_MONTH, 10, RoundingMode.HALF_UP);
        BigDecimal sumWithNDFL = sumWithNDFLForOneDay.multiply(BigDecimal.valueOf(daysOfVacation));
        return sumWithNDFL.multiply(BigDecimal.valueOf(1 - NDFL)).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * фразу из тз "При запросе также можно указать точные дни ухода в отпуск,
     * тогда должен проводиться рассчет отпускных с учётом праздников и выходных."
     * поняла как указывается день начала отпуска и день выхода на работу (в расчет отпускных не входит),
     * при этом если указано количество дней отпуска, оно не учитывается, а рассчитывается
     * с учетом праздничных дней. При расчете отпуска по ТК РФ выходные дни включаются в отпуск, праздники нет
     * <p>
     * уточнить тз нет возможности, в реальной жизни задала бы вопрос
     */
    int getCountDays(LocalDate dayStart, LocalDate dayEnd) {
        Predicate<LocalDate> daysNotHolidays = date ->
                !(date.getDayOfMonth() == 1 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 2 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 3 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 4 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 5 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 6 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 7 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 8 && date.getMonth().equals(Month.JANUARY))
                        && !(date.getDayOfMonth() == 23 && date.getMonth().equals(Month.FEBRUARY))
                        && !(date.getDayOfMonth() == 8 && date.getMonth().equals(Month.MARCH))
                        && !(date.getDayOfMonth() == 1 && date.getMonth().equals(Month.MAY))
                        && !(date.getDayOfMonth() == 9 && date.getMonth().equals(Month.MAY))
                        && !(date.getDayOfMonth() == 12 && date.getMonth().equals(Month.JUNE))
                        && !(date.getDayOfMonth() == 4 && date.getMonth().equals(Month.NOVEMBER));

        return (int) dayStart.datesUntil(dayEnd)
                .filter(daysNotHolidays)
                .count();
    }
}
