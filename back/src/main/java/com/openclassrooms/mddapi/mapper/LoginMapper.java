package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.responses.LoginResponseDto;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface LoginMapper extends EntityMapper<LoginResponseDto, User> {
}
