package com.microservices.mapper;

import com.microservices.dto.RegistrationRequest;
import com.microservices.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    RegistrationRequest mapToDTO(User entity);
    User mapToEntity(RegistrationRequest dto);
}
