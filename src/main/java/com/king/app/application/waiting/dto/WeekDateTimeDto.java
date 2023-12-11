package com.king.app.application.waiting.dto;

import com.king.app.infrastructure.api.common.dto.WeekDto;
import lombok.Getter;

@Getter
public class WeekDateTimeDto {
    private Integer year;
    private Integer month;
    private Integer week;
}
