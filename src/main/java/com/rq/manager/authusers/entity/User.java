package com.rq.manager.authusers.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "UUID", unique = true)
	private UUID id;
	
	@Column(name = "EMAIL", length = 100,
			unique = true, nullable = false)
	private String email;
	
	@Column(name = "PASSWORD", length = 50,
			nullable = false)
	@Size(min = 8, max = 50)
	private String password;
	
	@Column(name = "NAME", length = 80)
	private String name;
	
	@Column(name = "LASTNAME", length = 150)
	private String lastname;
	
	@Column(name = "USERNAME", length = 100, 
			unique = true, nullable = false)
	private String username;
	
	@Column(name = "NUM_PHONE",length = 9,
			unique = true)
	private String numPhone;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
}
