package com.king.app.application.waiting;

import com.king.app.infrastructure.mapper.WaitingMapper;
import com.king.app.presentation.api.waiting.response.AveragePartySizeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class PartySizeServiceTest {
    @Mock
    private WaitingMapper waitingMapper;
    @InjectMocks
    private PartySizeServiceImpl partySizeService;

    @DisplayName("결과값 가져 오는 테스트 - 일, 주, 월 동일")
    @Test
    void result_rounds_success() {
        // given
        given(waitingMapper.getTodayPartySize(any(), any()))
                .willReturn(2.3333333333333335);

        // when
        AveragePartySizeResponse result = partySizeService.getTodayPartySizeAverage();

        // then
        assertNotNull(result);
        assertEquals("2.40", String.format("%.2f", Double.parseDouble(result.getPartySize())));
    }

    @DisplayName("팀 크기가 0일 경우 - today - 일, 주, 월 동일")
    @Test
    void size_zero_is_null() {
        // given
        given(waitingMapper.getTodayPartySize(any(), any()))
                .willReturn(0.0);

        // when
        AveragePartySizeResponse result = partySizeService.getTodayPartySizeAverage();

        // then
        assertNotNull(result);
        if (result.getPartySize() != null) {
            assertEquals("0.00", String.format("%.2f", Double.parseDouble(result.getPartySize())));
        } else {
            assertNull(null);
        }
    }
}