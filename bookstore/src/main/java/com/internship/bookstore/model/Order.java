package com.internship.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private double total;

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<OrderItem> orderItems;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Order(Long orderId, double total, Date orderDate) {
		this.orderId = orderId;
		this.total = total;
		this.orderDate = orderDate;
	}

}
