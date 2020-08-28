package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUpdateBookDTO {

	@NotEmpty(message = "Title of book is required")
	private String name;
	@NotNull(message = "Price is required")
	private double price;
	@NotNull(message = "Amount is required")
	private int amount;
	private boolean deleted;
	@NotNull(message = "Categories are required")
	private Set<Long> categoryIds;
	@NotNull(message = "Authors are required")
	private Set<Long> authorIds;

}
