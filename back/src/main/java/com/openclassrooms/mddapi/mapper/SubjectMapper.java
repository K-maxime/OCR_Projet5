package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.responses.SubjectResponseDto;
import com.openclassrooms.mddapi.models.Subject;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

@Component
@Mapper(componentModel = "spring")
public interface SubjectMapper extends EntityMapper<SubjectResponseDto,Subject> {
}
