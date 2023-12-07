package com.king.app.presentation.api.waiting.controller;

import com.king.app.application.api.waiting.service.WaitTimeService;
import com.king.app.application.api.waiting.service.dto.WeekDateTimeDto;
import com.king.app.presentation.api.waiting.request.MonthDateTimeRequest;
import com.king.app.presentation.api.waiting.response.AverageWaitTimeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting-average-hour")
public class WaitTimeAnalyticsController {
    private final WaitTimeService waitTimeService;

    @GetMapping("/today")
    public ResponseEntity<List<AverageWaitTimeResponse>> getTodayHourCountList() {
        List<AverageWaitTimeResponse> allTodayWaitTime = waitTimeService
                .findAllTodayWaitTime();
        return ResponseEntity.ok(allTodayWaitTime);
    }

    @GetMapping("/week")
    public ResponseEntity<List<AverageWaitTimeResponse>> getWeekHourCountList(
            @RequestBody WeekDateTimeDto date) {
        List<AverageWaitTimeResponse> allTodayWaitTime = waitTimeService
                .findAllWeekWaitTime(date);
        return ResponseEntity.ok(allTodayWaitTime);
    }

    @GetMapping("/month")
    public ResponseEntity<List<AverageWaitTimeResponse>> getMonthHourCountList(
            @RequestBody MonthDateTimeRequest date) {
        List<AverageWaitTimeResponse> allTodayWaitTime = waitTimeService
                .findAllMonthWaitTime(date);
        return ResponseEntity.ok(allTodayWaitTime);
    }
}
