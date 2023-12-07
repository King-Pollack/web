package com.king.app.application.api.waiting.service;

import com.king.app.application.api.waiting.service.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AveragePartySizeResponse;

public interface PartySizeService {
    /**
     * 한 팀에 몇 명인지 평균 (금일)
     *
     * @return String size
     */
    AveragePartySizeResponse getTodayPartySizeAverage();

    /**
     * 한 팀에 몇 명인지 평균 (주 마다)
     *
     * @param date (year, month, week)
     * @return String size
     */
    AveragePartySizeResponse getWeekPartySizeAverage(WeekDateTimeDto date);

    /**
     * 한 팀에 몇 명인지 평균 (달 마다)
     *
     * @param date (year, month)
     * @return String size
     */
    AveragePartySizeResponse getMonthPartySizeAverage(MonthDateTimeRequest date);
}
