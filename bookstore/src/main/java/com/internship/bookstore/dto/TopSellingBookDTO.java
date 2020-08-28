package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingBookDTO {
	
	private String bookName;
	private Set<AuthorDTO> authors;
	private int amount;
	
}
