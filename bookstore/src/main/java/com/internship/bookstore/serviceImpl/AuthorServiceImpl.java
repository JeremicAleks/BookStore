package com.internship.bookstore.serviceImpl;

import com.internship.bookstore.dto.AuthorDTO;
import com.internship.bookstore.dto.AuthorListDTO;
import com.internship.bookstore.exception.StoreException;
import com.internship.bookstore.mapper.AuthorMapper;
import com.internship.bookstore.model.Author;
import com.internship.bookstore.repository.AuthorRepository;
import com.internship.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	AuthorMapper authorMapper;

	@Override
	public AuthorListDTO findAllAuthors() {
		AuthorListDTO authorListDTO = new AuthorListDTO();
		List<Author> authors = authorRepository.findAll();
		for (Author author : authors) {
			authorListDTO.getAuthors().add(authorMapper.map(author));
		}
		return authorListDTO;
	}

	@Override
	public AuthorDTO getOne(Long id) {
		Author author = authorRepository.getOne(id);
		if (author != null)
			return authorMapper.map(author);
		else
			throw new StoreException(HttpStatus.NOT_FOUND, "Author doesn't exist!");
	}
}
