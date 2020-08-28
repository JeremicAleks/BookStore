package com.internship.bookstore.service;


import com.internship.bookstore.dto.AuthorDTO;
import com.internship.bookstore.dto.AuthorListDTO;

public interface AuthorService {

	public AuthorListDTO findAllAuthors();

	public AuthorDTO getOne(Long id);

}
