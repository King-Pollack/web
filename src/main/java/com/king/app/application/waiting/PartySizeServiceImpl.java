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
        return getPartySizeResponse(todayPartySize);
    }

    @Override
    public AveragePartySizeResponse getWeekPartySizeAverage(WeekDateTimeDto date) {
        WeekDto initWeek = WeekDto
                .createInitializedWeekDto(date.getYear(), date.getMonth(), date.getWeek());
        Double weekPartySize = waitingMapper.getWeekPartySize(initWeek);
        return getPartySizeResponse(weekPartySize);
    }

    @Override
    public AveragePartySizeResponse getMonthPartySizeAverage(MonthDateTimeRequest date) {
        Double monthPartySize = waitingMapper.getMonthPartySize(date.getYear(), date.getMonth());
        return getPartySizeResponse(monthPartySize);
    }

    private AveragePartySizeResponse getPartySizeResponse(Double todayPartySize) {
        if (todayPartySize == null) {
            log.error("NPE 발생");
            return null;
        }
        Double partySize = Math.ceil(todayPartySize * 10.0) / 10.0;
        return AveragePartySizeResponse.builder()
                .partySize(partySize.toString())
                .build();
    }
}
