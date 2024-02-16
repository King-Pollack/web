package com.king.app.infrastructure.api.common.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class WeekDto {
    private LocalDateTime firstDay;
    private LocalDateTime lastDay;
    public static WeekDto createInitializedWeekDto(int year, int month, int week) {
        WeekDto weekDto = new WeekDto();
        weekDto.initializeWeekDays(year, month, week);
        return weekDto;
    }

    private void initializeWeekDays(int year, int month, int week) {
        int startDay = (week - 1) * 7 + 1;

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate startDate = firstDayOfMonth.withDayOfMonth(startDay);

        int endDay = startDay + 6;
        LocalDate endDate;

        if (endDay > firstDayOfMonth.lengthOfMonth()) {
            endDate = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        } else {
            endDate = firstDayOfMonth.withDayOfMonth(endDay);
        }
        if (week == 5) {
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }
        this.firstDay = startDate.atStartOfDay();
        this.lastDay = endDate.atTime(23, 59, 59);
    }
}
