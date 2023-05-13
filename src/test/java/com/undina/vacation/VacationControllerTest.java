package com.undina.vacation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.undina.vacation.dto.VacationDto;
import com.undina.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class VacationControllerTest {
    @Autowired
    private final VacationService vacationService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @Test
    void getAmountOfVacationPayNoDate() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .daysOfVacation(10)
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("14846.42"));
    }

    @Test
    void getAmountOfVacationPayDateStartAndDateEnd() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .daysOfVacation(10)
                .dayStart(LocalDate.of(2023, 5, 1))
                .dayEnd(LocalDate.of(2023, 5, 5))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("4453.92"));
    }

    @Test
    void getAmountOfVacationPayDateStartAndDays() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .daysOfVacation(10)
                .dayStart(LocalDate.of(2023, 5, 1))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("11877.13"));
    }

    @Test
    void getAmountOfVacationPayDateEndAndDays() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .daysOfVacation(9)
                .dayEnd(LocalDate.of(2023, 5, 2))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("11877.13"));
    }

    @Test
    void getAmountOfVacationPayNullDaysAndNullStartException() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .dayEnd(LocalDate.of(2023, 5, 2))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAmountOfVacationPayNullDaysAndNullEndException() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .averageSalary(BigDecimal.valueOf(50000))
                .dayStart(LocalDate.of(2023, 5, 2))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAmountOfVacationPayNullSalaryException() throws Exception {
        VacationDto vacationDto = VacationDto.builder()
                .daysOfVacation(9)
                .dayEnd(LocalDate.of(2023, 5, 2))
                .build();

        mvc.perform(get("/calculacte")
                .content(mapper.writeValueAsString(vacationDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(MethodArgumentNotValidException.class));
    }

}
