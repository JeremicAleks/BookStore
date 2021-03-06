package com.internship.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.bookstore.config.*;
import com.internship.bookstore.dto.OrderReportDTO;
import com.internship.bookstore.dto.OrderResponseDTO;
import com.internship.bookstore.service.OrderService;
import com.internship.bookstore.serviceImpl.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-sources/snippets")
public class OrderControllerTest {

	String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
	HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
	CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

	@MockBean
	CustomUserDetailsService userDetailsService;

	@Autowired
    MockMvc mockMvc;

	@MockBean
	OrderService orderService;

	@Autowired
    ObjectMapper objectMapper;

	@Test
	@WithMockUser
	public void addOrder() throws Exception {
		when(orderService.addOrder(any(), any())).thenReturn(new OrderResponseDTO(OrderConstants.order0orderId));
		mockMvc.perform(post(UrlPrefix.GET_ORDERS).accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(OrderConstants.orderListDTO)).sessionAttr(TOKEN_ATTR_NAME, csrfToken)
				.param(csrfToken.getParameterName(), csrfToken.getToken())).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.orderId").value(OrderConstants.order0orderId))
				.andDo(document("{class-name}/{method-name}"));
	}


	@Test
	@WithMockUser(roles = "ADMIN")
	public void getProcessedOrdersTest() throws Exception {
		OrderReportDTO orDTO = new OrderReportDTO();
		orDTO.setOrderDTOList(OrderConstants.orderDTOList);
		when(orderService.getOrderReport()).thenReturn(orDTO);

		mockMvc.perform(get(UrlPrefix.GET_ORDERS)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(OrderConstants.orderDTOList)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.orderDTOList[0].orderId").value(OrderConstants.order0orderId))
			.andExpect(jsonPath("$.orderDTOList[0].orderDate").value(OrderConstants.order0Date))
			.andExpect(jsonPath("$.orderDTOList[0].orderPrice").value(OrderConstants.order0Price))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].orderItemId").value(OrderConstants.order0orderItem0Id))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].orderId").value(OrderConstants.order0orderId))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].orderedAmount").value(OrderConstants.order0orderItem0amount))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].totalOrderedItemPrice").value(OrderConstants.order0orderItem0amount * BookConstants.book0amount))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.bookId").value(BookConstants.book0id))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.name").value(BookConstants.book0name))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.price").value(BookConstants.book0price))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.amount").value(BookConstants.book0amount))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.deleted").value(BookConstants.book0deleted))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.authors[0].authorId").value(AuthorConstants.PERA_ID))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.authors[0].firstName").value(AuthorConstants.FIRST_NAME_PERA))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.authors[0].lastName").value(AuthorConstants.LAST_NAME_PERA))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.categories[0].categoryId").value(CategoryConstants.category0id))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.categories[0].name").value(CategoryConstants.category0name))
			.andExpect(jsonPath("$.orderDTOList[0].orderItemDTOList[0].bookDTO.categories[0].deleted").value(CategoryConstants.category0isDeleted))
			.andExpect(jsonPath("$.orderDTOList[0].user.username").value("test"))
			.andExpect(jsonPath("$.orderDTOList[0].user.userId").value(1L))
			.andDo(document("{class-name}/{method-name}",preprocessResponse(prettyPrint()),
					responseFields(bookReportDTOResponseFields())));

	}

	 private FieldDescriptor[] bookReportDTOResponseFields() {
		    return new FieldDescriptor[] {
		    		fieldWithPath("orderDTOList.[].orderId").description("The unique identifier of the order"),
		    		fieldWithPath("orderDTOList.[].orderDate").description("Date when the order has been performed"),
		    		fieldWithPath("orderDTOList.[].orderPrice").description("Date when the order has been performed"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].orderItemId").description("The unique identifier of the order item"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].orderId").description("The unique identifier of the order"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].orderedAmount").description("Amount of ordered item"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].totalOrderedItemPrice").description("Total price of ordered items/books"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.bookId").description("The unique identifier of the order item/book"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.name").description("Name of the order item/book"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.price").description("Price of the order item/book"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.amount").description("Amount of the ordered items/books"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.deleted").description("Check if item/book has been deleted"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.authors.[].authorId").description("ID of book's author "),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.authors.[].firstName").description("Item/book author's first name"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.authors.[].lastName").description("Item/book author's last name"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.categories.[].categoryId").description("Item/book category id"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.categories.[].name").description("Item/book category name"),
		    		fieldWithPath("orderDTOList.[].orderItemDTOList.[].bookDTO.categories.[].deleted").description("Check if item/book  category has been deleted"),
		    		fieldWithPath("orderDTOList.[].user.userId").description("ID of user who made the order"),
		    		fieldWithPath("orderDTOList.[].user.username").description("Username of the user who made the order"),
		    };
		  }

	@Test
	@WithMockUser(roles = "ADMIN")
	public void getProcessedOrdersReportTest() throws Exception {
		OrderReportDTO orDTO = new OrderReportDTO();
		orDTO.setOrderDTOList(OrderConstants.orderDTOList);
		when(orderService.getOrderReport()).thenReturn(orDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=orders.pdf");

		mockMvc.perform(get(UrlPrefix.GET_ORDERS + "/pdf").accept(MediaType.APPLICATION_PDF)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_PDF_VALUE))
				.andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint())));
	}

}
