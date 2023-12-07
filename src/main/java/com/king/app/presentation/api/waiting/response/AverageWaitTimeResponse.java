package com.king.app.presentation.api.waiting.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AverageWaitTimeResponse {
    private String hour;
    private String count;
}
