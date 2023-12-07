package com.king.app.mapper;

import com.king.app.infrastructure.api.common.dto.WeekDto;
import com.king.app.presentation.api.waiting.dto.WaitTimeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WaitingMapper {
    /*
    입장하기까지 평균 mapper
     */
    Double calculateTodayAverageEntryTime(@Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("now") LocalDateTime now);

    Double calculateWeekAverageEntryTime(@Param("weekDto") WeekDto weekDto);

    Double calculateMonthAverageEntryTime(@Param("year") Integer year,
                                          @Param("month") Integer month);

    /*
    한 팀에 몇 명이었는지 평균 mapper
     */

    Double getTodayPartySize(@Param("startOfDay") LocalDateTime startOfDay,
                             @Param("now") LocalDateTime now);

    Double getWeekPartySize(@Param("weekDto") WeekDto weekDto);

    Double getMonthPartySize(@Param("year") Integer year,
                             @Param("month") Integer month);

    /*
    대기하기 누른 평균 시간 mapper
     */

    List<WaitTimeDto> findAllTodayWaitTime(@Param("now") LocalDate now);

    List<WaitTimeDto> findAllWeekWaitTime(@Param("weekDto") WeekDto weekDto);

    List<WaitTimeDto> findAllMonthWaitTime(@Param("year") Integer year,
                                           @Param("month") Integer month);
}
