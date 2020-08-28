package com.internship.bookstore.service;


import com.internship.bookstore.dto.OrderListDTO;
import com.internship.bookstore.dto.OrderReportDTO;
import com.internship.bookstore.dto.OrderResponseDTO;

public interface OrderService {

	OrderResponseDTO addOrder(OrderListDTO orderRequest, String username);

	OrderReportDTO getOrderReport();
	
}
