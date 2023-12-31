package com.king.app.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    CONFIRMED("확인"),
    CANCELED("취소"),
    CHANGED("변경");
    private final String value;
}
