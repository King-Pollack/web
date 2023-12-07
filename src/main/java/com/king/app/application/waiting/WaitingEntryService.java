package com.king.app.application.waiting;

import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.application.waiting.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.response.AverageEntryTimeResponse;

public interface WaitingEntryService {
    /**
     * 대기 -> 입장 평균 시간 계산 (금일)
     *
     * @return String minutes, seconds
     */
    AverageEntryTimeResponse getTodayAverageEntryTime();

    /**
     * 대기 -> 입장 평균 시간 계산 (주 마다)
     *
     * @param date (year, month, week)
     * @return String minutes, seconds
     */
    AverageEntryTimeResponse getWeekAverageEntryTime(WeekDateTimeDto date);

    /**
     * 대기 -> 입장 평균 시간 계산 (달 마다)
     *
     * @param date (year, month)
     * @return String minutes, seconds
     */
    AverageEntryTimeResponse getMonthAverageEntryTime(MonthDateTimeRequest date);
}
