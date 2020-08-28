package com.internship.bookstore.repository;

import com.internship.bookstore.model.OrderItem;
import com.internship.bookstore.utils.SalesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
	@Query("SELECT NEW com.internship.bookstore.utils.SalesDetails(oi.book.bookId, SUM(oi.amount)) FROM OrderItem oi JOIN oi.book b WHERE b.amount > 0 AND b.isDeleted = false GROUP BY oi.book.bookId ORDER BY SUM(oi.amount) DESC")
    public List<SalesDetails> getTopSellingBooks();
	
//	@Query("SELECT NEW com.levi9.prodavnica.dto.OrderItemDTO(oi.orderId, oi.orderDate, oi) FROM OrderItem o ")
//	public List<OrderItemDTO> getAllOrdersForReport();
	
}
