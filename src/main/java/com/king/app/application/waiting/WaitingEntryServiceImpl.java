package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WeekDateTimeDto;
import com.king.app.infrastructure.api.common.dto.WeekDto;
import com.king.app.infrastructure.mapper.WaitingMapper;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AverageEntryTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WaitingEntryServiceImpl implements WaitingEntryService {
    private final WaitingMapper waitingMapper;

    @Override
    public AverageEntryTimeResponse getTodayAverageEntryTime() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now();
        Double averageEntryTime = waitingMapper.calculateTodayAverageEntryTime(startOfDay, endOfDay);
        return getAverageEntryTimeResponse(averageEntryTime);
    }

    @Override
    public AverageEntryTimeResponse getWeekAverageEntryTime(WeekDateTimeDto date) {
        WeekDto initWeek = WeekDto
                .createInitializedWeekDto(date.getYear(), date.getMonth(), date.getWeek());
        Double averageEntryTime = waitingMapper.calculateWeekAverageEntryTime(initWeek);
        return getAverageEntryTimeResponse(averageEntryTime);
    }

    @Override
    public AverageEntryTimeResponse getMonthAverageEntryTime(MonthDateTimeRequest date) {
        Double averageEntryTime = waitingMapper
                .calculateMonthAverageEntryTime(date.getYear(), date.getMonth());
        return getAverageEntryTimeResponse(averageEntryTime);
    }

    private static AverageEntryTimeResponse getAverageEntryTimeResponse(Double averageEntryTime) {
        if (averageEntryTime == null) {
            // global exception 처리 요망
            System.out.println("NPE 떴음");
            return null;
        }
        String strMinutes = Integer.toString((int) (averageEntryTime / 60));
        String strSeconds = Integer.toString((int) (averageEntryTime % 60));
        return AverageEntryTimeResponse.builder()
                .minutes(strMinutes)
                .seconds(strSeconds)
                .build();
    }
}
