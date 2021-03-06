package com.internship.bookstore.dto;

import com.internship.bookstore.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long userId;
	private String username;
	
	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
	}
	
}
