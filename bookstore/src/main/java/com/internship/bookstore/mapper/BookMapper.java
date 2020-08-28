package com.internship.bookstore.mapper;

import com.internship.bookstore.dto.BookDTO;
import com.internship.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, AuthorMapper.class })
public interface BookMapper {
	BookDTO map(Book book);

	Book map(BookDTO source);

}
