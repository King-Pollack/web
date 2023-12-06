package com.king.app.infrastructure.api.common.dto;

import lombok.Getter;

@Getter
public class WeekDateTimeDto {
    private Integer year;
    private Integer month;
    private Integer week;

    public WeekDto toWeekDto() {
        WeekDto weekDto = new WeekDto();
        return weekDto.getWeekDays(year, month, week);
    }
}
