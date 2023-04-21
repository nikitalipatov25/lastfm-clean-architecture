package com.nikitalipatov.common.dto;

import com.nikitalipatov.common.enums.EventType;
import com.nikitalipatov.common.enums.Status;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaMessage {
    private List<String> trackList;
    private String albumId;
    private String artistId;
    private Status status;
    private EventType eventType;
}
