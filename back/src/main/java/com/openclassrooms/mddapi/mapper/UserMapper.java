package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.responses.UserDetailResponseDto;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDetailResponseDto, User> {
}
