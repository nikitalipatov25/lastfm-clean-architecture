package com.nikitalipatov.common.dto;

import com.nikitalipatov.common.enums.EventType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaMessage {
    private String albumId;
    private String albumName;
    private String artistName;
    private EventType eventType;
}
