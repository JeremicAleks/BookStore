package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TopSellingBookListDTO {
	
	List<TopSellingBookDTO> topSellingBookList;
	
	public TopSellingBookListDTO() {
		this.topSellingBookList = new ArrayList<>();
	}
	
}
