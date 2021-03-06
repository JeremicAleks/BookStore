package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookListDTO {

	List<BookDTO> books;

	public BookListDTO() {
		this.books = new ArrayList<>();
	}

}
