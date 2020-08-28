package com.internship.bookstore.controller;

import com.internship.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

	@Autowired
	AuthorService authorService;

	@GetMapping
	public ResponseEntity<?> getAllAuthors() {
		return ResponseEntity.ok(authorService.findAllAuthors());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAuthor(@PathVariable Long id) {
		return ResponseEntity.ok(authorService.getOne(id));
	}

}
