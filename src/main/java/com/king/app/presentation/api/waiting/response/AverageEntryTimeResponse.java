package com.king.app.presentation.api.waiting.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AverageEntryTimeResponse {
    private String minutes;
    private String seconds;
}
