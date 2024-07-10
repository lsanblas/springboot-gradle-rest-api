package dev.example.restapi.mapper;

import dev.example.restapi.model.user.UserEntity;
import dev.example.restapi.model.user.UserRequest;
import dev.example.restapi.model.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity UserRequesttoUserEntity(UserRequest userRequest);

    @Mapping(target = "id", source = "UUID")
    UserResponse UserEntitytoUserResponse(UserEntity userEntity);
}
