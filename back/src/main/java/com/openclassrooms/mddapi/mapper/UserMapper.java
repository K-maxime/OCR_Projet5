package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.responses.UserResponseDto;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserResponseDto, User> {
}
