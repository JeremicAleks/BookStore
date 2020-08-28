package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
	private Long orderId;
	private List<OrderItemDTO> orderItemDTOList;
	private String orderDate;
	private double orderPrice;
	private UserDTO user;

	public OrderDTO() {
		this.orderItemDTOList = new ArrayList<>();
	}
	
}
