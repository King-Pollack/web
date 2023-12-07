package com.king.app.application.api.waiting.service;

import com.king.app.application.api.waiting.service.dto.WeekDateTimeDto;
import com.king.app.infrastructure.api.common.dto.WeekDto;
import com.king.app.mapper.WaitingMapper;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AveragePartySizeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
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
        WeekDto weekDto = date.toWeekDto();
        Double partySize = Math.ceil(waitingMapper.getWeekPartySize(weekDto) * 10.0) / 10.0;
        return getAveragePartySizeResponse(partySize);
    }

    @Override
    public AveragePartySizeResponse getMonthPartySizeAverage(MonthDateTimeRequest date) {
        Double monthPartySize = waitingMapper.getMonthPartySize(date);
        return getAveragePartySizeResponse(monthPartySize);
    }

    private static AveragePartySizeResponse getAveragePartySizeResponse(Double partySize) {
        if (partySize == null) {
            System.out.println("NPE 떴음");
            return null;
        }
        return AveragePartySizeResponse.builder()
                .partySize(partySize.toString()).build();
    }
}
