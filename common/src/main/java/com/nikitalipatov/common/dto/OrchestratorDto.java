package com.nikitalipatov.common.dto;

import com.nikitalipatov.common.enums.EventType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrchestratorDto {

    private String id;
    private EventType event;
    private String artistStatus;
    private String albumStatus;
    private String trackStatus;
}
