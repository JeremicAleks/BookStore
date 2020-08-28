package com.internship.bookstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

	private Long bookId;
	private String name;
	private double price;
	private int amount;
	private boolean deleted;
	private Set<AuthorDTO> authors = new HashSet<>();
	private Set<CategoryDTO> categories = new HashSet<>();

	public BookDTO(Long bookId, String name, double price, int amount, boolean deleted) {
		this.bookId = bookId;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.deleted = deleted;
	}

}
