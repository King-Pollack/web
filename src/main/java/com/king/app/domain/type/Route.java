package com.king.app.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Route {
    COM("컴퓨터"),
    MOBILE("모바일");
    private final String description;
}
