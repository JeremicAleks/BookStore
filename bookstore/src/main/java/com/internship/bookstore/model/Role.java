package com.internship.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role {
	
	@Id
	private Long roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> users;
	
}
