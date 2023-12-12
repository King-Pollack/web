package com.king.app.presentation.api.waiting.controller;

import com.king.app.application.waiting.PartySizeService;
import com.king.app.application.waiting.dto.WeekDateTimeDto;
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
@RequestMapping("/api/waiting-average-party-size")
public class PartySizeAnalyticsController {
    private final PartySizeService partySizeService;

    @GetMapping("/today")
    public ResponseEntity<AveragePartySizeResponse> getPartySizeToday() {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getTodayPartySizeAverage();
        return ResponseEntity.ok(weekPartySizeAverage);
    }

    @GetMapping("/week")
    public ResponseEntity<AveragePartySizeResponse> getPartySizeWeek(
            @RequestBody WeekDateTimeDto date) {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getWeekPartySizeAverage(date);
        return ResponseEntity.ok(weekPartySizeAverage);
    }

    @GetMapping("/month")
    public ResponseEntity<AveragePartySizeResponse> getPartySizeMonth(
            @RequestBody MonthDateTimeRequest date) {
        AveragePartySizeResponse weekPartySizeAverage =
                partySizeService.getMonthPartySizeAverage(date);
        return ResponseEntity.ok(weekPartySizeAverage);
    }
}
