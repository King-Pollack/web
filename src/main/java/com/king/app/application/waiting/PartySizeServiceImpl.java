package com.king.app.application.waiting;

import com.king.app.application.waiting.dto.WeekDateTimeDto;
import com.king.app.infrastructure.api.common.dto.WeekDto;
import com.king.app.infrastructure.mapper.WaitingMapper;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AveragePartySizeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartySizeServiceImpl implements PartySizeService {
    private final WaitingMapper waitingMapper;

    @Override
    public AveragePartySizeResponse getTodayPartySizeAverage() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime now = LocalDateTime.now();
        Double todayPartySize = waitingMapper.getTodayPartySize(startOfDay, now);
        return getAveragePartySizeResponse(todayPartySize);
    }

    @Override
    public AveragePartySizeResponse getWeekPartySizeAverage(WeekDateTimeDto date) {
        WeekDto initWeek = WeekDto
                .createInitializedWeekDto(date.getYear(), date.getMonth(), date.getWeek());
        Double weekPartySize = waitingMapper.getWeekPartySize(initWeek);
        if (weekPartySize == null) {
            log.error("NPE 발생");
            return null;
        }
        Double partySize = Math.ceil(weekPartySize * 10.0) / 10.0;
        return getAveragePartySizeResponse(partySize);
    }

    @Override
    public AveragePartySizeResponse getMonthPartySizeAverage(MonthDateTimeRequest date) {
        Double monthPartySize = waitingMapper.getMonthPartySize(date.getYear(), date.getMonth());
        return getAveragePartySizeResponse(monthPartySize);
    }

    private static AveragePartySizeResponse getAveragePartySizeResponse(Double partySize) {
        if (partySize == null) {
            log.error("NPE 발생");
            return null;
        }
        return AveragePartySizeResponse.builder()
                .partySize(partySize.toString()).build();
    }
}
