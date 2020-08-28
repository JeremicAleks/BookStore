package com.internship.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;

	private int amount;

	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

}
