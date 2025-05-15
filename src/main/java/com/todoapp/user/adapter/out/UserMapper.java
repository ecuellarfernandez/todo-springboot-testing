package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // El orden de los parámetros en User es: id, username, name, email, password
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User entityToDomain(UserEntity userEntity);

    // Convertir de dominio a entidad
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserEntity domainToEntity(User user);

    // Lista de entidades a lista de dominio
    List<User> entitiesToDomains(List<UserEntity> userEntities);
}