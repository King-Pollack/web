package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WeekDateTimeDto;
import com.king.app.infrastructure.api.common.dto.WeekDto;
import com.king.app.infrastructure.mapper.WaitingMapper;
import com.king.app.presentation.api.waiting.dto.WaitTimeDto;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AverageWaitTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitTimeServiceImpl implements WaitTimeService{
    private final WaitingMapper waitingMapper;
    @Override
    public List<AverageWaitTimeResponse> findAllTodayWaitTime() {
        LocalDate now = LocalDate.now();
        List<AverageWaitTimeResponse> list = new ArrayList<>();
        List<WaitTimeDto> allTodayWaitTime = waitingMapper
                .findAllTodayWaitTime(now);
        for (WaitTimeDto waitTimeDto : allTodayWaitTime) {
            AverageWaitTimeResponse build = AverageWaitTimeResponse.builder()
                    .hour(waitTimeDto.getHour().toString())
                    .count(waitTimeDto.getCount().toString())
                    .build();
            list.add(build);
        }
        return list;
    }

    @Override
    public List<AverageWaitTimeResponse> findAllWeekWaitTime(WeekDateTimeDto date) {
        WeekDto initWeek = WeekDto
                .createInitializedWeekDto(date.getYear(), date.getMonth(), date.getWeek());
        int lastDay = initWeek.getLastDay().getDayOfMonth();
        int firstDay = initWeek.getFirstDay().getDayOfMonth();
        Integer days = lastDay - firstDay + 1;
        List<AverageWaitTimeResponse> list = new ArrayList<>();
        List<WaitTimeDto> allWeekWaitTime =
                waitingMapper.findAllWeekWaitTime(initWeek);
        for (WaitTimeDto waitTimeDto : allWeekWaitTime) {
            // 일주일 간 count 수 가져오기
            Integer count1 = waitTimeDto.getCount();
            // 일 수만큼 나누기
            Double dayCountAverage = (double) count1 / days;
            // 소수점 반올림
            Double count = Math.round(dayCountAverage * 10) / 10.0;
            AverageWaitTimeResponse build = AverageWaitTimeResponse.builder()
                    .hour(waitTimeDto.getHour().toString())
                    .count(count.toString())
                    .build();
            list.add(build);
        }
        return list;
    }

    @Override
    public List<AverageWaitTimeResponse> findAllMonthWaitTime(MonthDateTimeRequest date) {
        LocalDate lastDayOfMonth = getLastDayOfMonth(date.getYear(), date.getMonth());
        Integer days = lastDayOfMonth.getDayOfMonth();
        List<AverageWaitTimeResponse> list = new ArrayList<>();
        List<WaitTimeDto> allMonthWaitTime = waitingMapper.findAllMonthWaitTime(date.getYear(), date.getMonth());
        for (WaitTimeDto waitTimeDto : allMonthWaitTime) {
            Integer count = waitTimeDto.getCount();
            Double divideCount = (double) count / days;
            Double countResult = Math.round(divideCount * 100) / 100.0;
            AverageWaitTimeResponse build = AverageWaitTimeResponse.builder()
                    .hour(waitTimeDto.getHour().toString())
                    .count(countResult.toString())
                    .build();
            list.add(build);
        }
        return list;
    }
    private LocalDate getLastDayOfMonth(int year, int month) {
        return YearMonth.of(year, month).atEndOfMonth();
    }
}
