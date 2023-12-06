package com.king.app.presentation.api.waiting.controller;

import com.king.app.application.api.waiting.service.WaitingEntryService;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.infrastructure.api.common.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.response.AverageEntryTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting/average")
public class WaitingEntryAnalyticsController {
    private final WaitingEntryService waitingEntryService;

    @GetMapping("/entry/time/today")
    public ResponseEntity<Object> getTodayAverageEntryTime() {
        AverageEntryTimeResponse averageEntryTimeResponse =
                waitingEntryService.getTodayAverageEntryTime();
        return ResponseEntity.ok(averageEntryTimeResponse);
    }

    @GetMapping("/entry/time/week")
    public ResponseEntity<Object> getWeekAverageEntryTime(
            @RequestBody WeekDateTimeDto date) {
        AverageEntryTimeResponse averageEntryTimeDto =
                waitingEntryService.getWeekAverageEntryTime(date);
        return ResponseEntity.ok(averageEntryTimeDto);
    }

    @GetMapping("/entry/time/month")
    public ResponseEntity<Object> getMonthAverageEntryTime(
            @RequestBody MonthDateTimeRequest date) {
        AverageEntryTimeResponse averageEntryTimeResponse =
                waitingEntryService.getMonthAverageEntryTime(date);
        return ResponseEntity.ok(averageEntryTimeResponse);
    }
}
