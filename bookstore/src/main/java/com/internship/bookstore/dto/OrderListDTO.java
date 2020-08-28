package com.internship.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {

	@NotNull(message = "Orders are required")
	private Set<AddOrderDTO> orders;
	@NotNull(message = "Total price is required")
	private double total;

}
