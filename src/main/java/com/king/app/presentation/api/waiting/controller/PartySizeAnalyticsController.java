package com.king.app.presentation.api.waiting.controller;

import com.king.app.application.api.waiting.service.PartySizeService;
import com.king.app.infrastructure.api.common.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AveragePartySizeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting/average")
public class PartySizeAnalyticsController {
    private final PartySizeService partySizeService;

    @GetMapping("/party/size/today")
    public ResponseEntity<Object> getPartySizeToday() {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getTodayPartySizeAverage();
        return ResponseEntity.ok(weekPartySizeAverage);
    }

    @GetMapping("/party/size/week")
    public ResponseEntity<Object> getPartySizeWeek(
            @RequestBody WeekDateTimeDto date) {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getWeekPartySizeAverage(date);
        return ResponseEntity.ok(weekPartySizeAverage);
    }

    @GetMapping("/party/size/month")
    public ResponseEntity<Object> getPartySizeMonth(
            @RequestBody MonthDateTimeRequest date) {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getMonthPartySizeAverage(date);
        return ResponseEntity.ok(weekPartySizeAverage);
    }
}
