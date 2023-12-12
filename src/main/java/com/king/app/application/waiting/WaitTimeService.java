package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AverageWaitTimeResponse;

import java.util.List;

public interface WaitTimeService {
    /**
     * 시간 별 대기 누른 시간 카운트 (금일)
     *
     * @return List String hour, count
     */
    List<AverageWaitTimeResponse> findAllTodayWaitTime();

    /**
     * 시간 별 대기 누른 시간 카운트 (주 마다)
     *
     * @param date (year, month, week)
     * @return List String hour, count (소수점 한 자리까지)
     */
    List<AverageWaitTimeResponse> findAllWeekWaitTime(WeekDateTimeDto date);

    /**
     * 시간 별 대기 누른 시간 카운트 (달 마다)
     *
     * @param date (year, month)
     * @return List String hour, count (소수점 두 자리까지)
     */
    List<AverageWaitTimeResponse> findAllMonthWaitTime(MonthDateTimeRequest date);
}
