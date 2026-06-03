package com.openclassrooms.mddapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean isSubscribed;
}
