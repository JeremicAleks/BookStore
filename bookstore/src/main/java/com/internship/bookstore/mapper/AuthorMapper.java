package com.internship.bookstore.mapper;

import com.internship.bookstore.dto.AuthorDTO;
import com.internship.bookstore.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO map(Author source);

}
