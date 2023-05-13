package com.undina.vacation.controller;

import com.undina.vacation.dto.VacationDto;
import com.undina.vacation.service.VacationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/calculacte")
@Validated
@Tag(name = "Vacation Controller", description = "Отвечает за расчет отпускных")
public class VacationController {
    private  final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @Operation(summary = "Позволяет рассчитать сумму отпускных")
    @GetMapping()
    BigDecimal getAmountOfVacationPay(@RequestBody @Valid VacationDto vacationDto) {
        log.info("get amount of vacation pay vacationDto={}", vacationDto);
        return vacationService.getAmountOfVacationPay(vacationDto);
    }

}
