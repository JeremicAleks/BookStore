package com.internship.bookstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.bookstore.config.OrderConstants;
import com.internship.bookstore.dto.OrderDTO;
import com.internship.bookstore.dto.OrderReportDTO;
import com.internship.bookstore.dto.OrderResponseDTO;
import com.internship.bookstore.exception.StoreException;
import com.internship.bookstore.mapper.BookMapper;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.Order;
import com.internship.bookstore.model.OrderItem;
import com.internship.bookstore.model.User;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.OrderRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.serviceImpl.CustomUserDetailsService;
import com.internship.bookstore.serviceImpl.OrderServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderServiceImpl.class)
public class OrderServiceImplTest {

	@MockBean
	CustomUserDetailsService userDetailsService;

	@Autowired
    MockMvc mockMvc;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@MockBean
	OrderRepository orderRepository;

	@MockBean
	BookRepository bookRepository;

	@MockBean
	UserRepository userRepository;

	@Autowired
	OrderService orderService;

	@Autowired
    ObjectMapper objectMapper;

	@MockBean
	BookMapper bookMapper;

	@Test
	public void whenCreateOrder_returnSuccess() throws Exception {
		when(bookRepository.getOne(any())).thenReturn(OrderConstants.bookResponseExpected);

		ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
		when(orderRepository.save(any())).thenReturn(OrderConstants.orderResponseExpected);

		when(userRepository.findOneByUsername(any())).thenReturn(new User());

		OrderResponseDTO responseReturned = orderService.addOrder(OrderConstants.orderRequest, "test");
		verify(orderRepository).save(captor.capture());

		Order orderRequestActual = captor.getValue();
		Set<OrderItem> orderItems = orderRequestActual.getOrderItems();

		OrderItem orderItemRequestActual = new OrderItem();
		for (OrderItem order : orderItems) {
			orderItemRequestActual.setAmount(order.getAmount());
			orderItemRequestActual.setBook(order.getBook());
		}

		// Verify request fields
		assertEquals(OrderConstants.order0total, orderRequestActual.getTotal(), 0.001);
		assertEquals(OrderConstants.order0amount, orderItemRequestActual.getAmount(), 0.001);
		assertEquals(OrderConstants.order0bookId, orderItemRequestActual.getBook().getBookId(), 0.001);

		// Verify response fields
		assertEquals(OrderConstants.order0orderId, responseReturned.getOrderId());
	}

	@Test
	public void given_orderServiceThrowsAnError_requestIsEmpty_throwAnError() throws Exception {
		when(bookRepository.getOne(any())).thenReturn(null);
		thrown.expect(StoreException.class);

		orderService.addOrder(OrderConstants.orderListNull, "test");
	}

	@Test
	public void given_orderServiceThrowsAnError_whenGetBook_throwAnError() throws Exception {
		when(bookRepository.getOne(any())).thenReturn(null);
		thrown.expect(StoreException.class);

		orderService.addOrder(OrderConstants.orderListDTO, "test");
	}

	@Test
	public void given_orderServiceThrowsAnError_whenAmountIsGreaterThanOnStock_throwAnError() throws Exception {
		when(bookRepository.getOne(any())).thenReturn(new Book(1L, "test", 1, 1, false));
		thrown.expect(StoreException.class);

		orderService.addOrder(OrderConstants.orderListDTO, "test");
	}

	@Test
	public void getOrderReportTest_success() throws Exception {
		when(orderRepository.findAll()).thenReturn(OrderConstants.getAllOrders());

		OrderReportDTO reportDto = orderService.getOrderReport();
		List<OrderDTO> orderDTOList = reportDto.getOrderDTOList();
		assertEquals(orderDTOList.get(0).getOrderId(), OrderConstants.order0orderId);

		assertEquals(orderDTOList.get(0).getOrderDate(), OrderConstants.order0Date);
		assertEquals(orderDTOList.get(0).getOrderItemDTOList().get(0).getOrderItemId(),
				OrderConstants.getOrderItemDTOList().get(0).getOrderItemId(), 0.001);
		assertEquals(orderDTOList.get(0).getOrderPrice(), OrderConstants.order0Price, 0.001);
	}

	@Test
	public void getOrderReportTest_throwException() throws Exception {
		when(orderRepository.findAll()).thenReturn(OrderConstants.getAllOrders_withNullDate());
		thrown.expect(StoreException.class);
		orderService.getOrderReport();
	}

}
