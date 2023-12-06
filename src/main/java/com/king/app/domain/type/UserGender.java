package com.king.app.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserGender {
    female("female"), male("male");
    private final String description;
    public static UserGender of(String description) {
        for (UserGender userGender : UserGender.values()) {
            if (userGender.getDescription().equals(description)) {
                return userGender;
            }
        }
        throw new IllegalArgumentException("Invalid UserGender description: " + description);
    }
}
