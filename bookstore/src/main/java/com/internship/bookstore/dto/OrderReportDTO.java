package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderReportDTO {
	
	private List<OrderDTO> orderDTOList;

	public OrderReportDTO() {
		this.orderDTOList = new ArrayList<OrderDTO>();
	}	
	
}
