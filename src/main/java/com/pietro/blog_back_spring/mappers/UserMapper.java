package com.pietro.blog_back_spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pietro.blog_back_spring.dtos.RegisterDto;
import com.pietro.blog_back_spring.entities.User;

@Mapper(componentModel="spring", uses = PasswordEncoderQualifier.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedByName = "encodePassword")
    User toUser(RegisterDto registerDto);

}