package com.undina.vacation.controller;

import com.undina.vacation.dto.VacationDto;
import com.undina.vacation.service.VacationService;

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
public class VacationController {
    private  final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping()
    BigDecimal getAmountOfVacationPay(@RequestBody @Valid VacationDto vacationDto) {
        log.info("get amount of vacation pay vacationDto={}", vacationDto);
        return vacationService.getAmountOfVacationPay(vacationDto);
    }

}
