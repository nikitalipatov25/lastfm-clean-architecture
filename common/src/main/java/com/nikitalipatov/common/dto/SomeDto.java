package com.nikitalipatov.common.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SomeDto {

    String artistName;
    String albumName;
    List<String> tracksList;
}
