package com.internship.bookstore.service;


import com.internship.bookstore.dto.AddUpdateBookDTO;
import com.internship.bookstore.dto.BookDTO;
import com.internship.bookstore.dto.BookListDTO;
import com.internship.bookstore.dto.TopSellingBookListDTO;

import java.security.Principal;
import java.util.Set;

public interface BookService {

	public BookListDTO findAllBooks(Principal principal);

	public BookDTO findBook(Long id);

	public boolean updateBook(AddUpdateBookDTO bookRequest, long idBook);

	boolean addBook(AddUpdateBookDTO addUpdateBookDTO);
	
	BookListDTO getBooksFilter(Set<Long> id, String search);
	
	public TopSellingBookListDTO getTopSellingBooks(int topSellingBooksLimit);
}
