package com.example.mapper;

import com.example.model.Contact;
import com.example.model.User;
import com.example.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "contact", source = "email", qualifiedByName = "emailToContact")
    User toEntity(UserDto dto);

    @Mapping(target = "email", source = "contact.email")
    UserDto toDto(User entity);

    List<UserDto> toDtoList(List<User> entities);

    @Named("emailToContact")
    default Contact emailToContact(String email) {
        if (email == null) return null;
        Contact contact = new Contact();
        contact.setEmail(email);
        return contact;
    }
}
