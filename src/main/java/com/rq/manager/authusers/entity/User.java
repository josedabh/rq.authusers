package com.rq.manager.authusers.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class User.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {

	/** The id. */
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name = "ID", unique = true)
	private UUID id;
	
	/** The email. */
	@Column(name = "EMAIL", length = 100,
			unique = true, nullable = false)
	private String email;
	
	/** The password. */
	@Column(name = "PASSWORD", length = 200,
			nullable = false)
	private String password;
	
	/** The name. */
	@Column(name = "NAME", length = 80)
	private String name;
	
	/** The lastname. */
	@Column(name = "LASTNAME", length = 150)
	private String lastname;
	
	/** The username. */
	@Column(name = "USERNAME", length = 100, 
			unique = true, nullable = false)
	private String username;
	
	/** The num phone. */
	@Column(name = "NUM_PHONE",length = 9,
			unique = true)
	private String numPhone;
	
	/** The points. */
	@Column(name = "POINTS")
	private Integer points;
	
	/** The rol. */
	@Enumerated(EnumType.STRING)
	private Rol rol;
}
