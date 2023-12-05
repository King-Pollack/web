package com.king.app.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserGender {
    female("여자"), male("남자");
    private final String description;
}
