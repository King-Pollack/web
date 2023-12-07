package com.king.app.application.api.waiting.service.dto;

import com.king.app.infrastructure.api.common.dto.WeekDto;
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
