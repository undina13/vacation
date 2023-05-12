package com.undina.vacation.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationDto {
    @NotNull
    @Min(1)
    private BigDecimal averageSalary;
    private Integer daysOfVacation;
    private LocalDate dayStart;
    private LocalDate dayEnd;
}
