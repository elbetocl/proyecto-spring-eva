package com.proyect.eva.mapper;

import com.proyect.eva.dto.PhoneDto;
import com.proyect.eva.dto.UserResponseDto;
import com.proyect.eva.entity.Phone;
import com.proyect.eva.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(source = "lastLogin", target = "last_login")
    UserResponseDto toResponseDto(User user);
    
    PhoneDto toPhoneDto(Phone phone);
}