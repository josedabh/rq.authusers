package com.rq.manager.authusers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REWARD")
public class Reward {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "NAME", length = 80)
	private String name;
	
	@Column(name = "DESCRIPTION", length = 200)
	private String description;
	
	@Column(name = "POINTS")
	private int points;
	
	@Column(name = "IMAGE", length = 200)
	private String image;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	@Column(name = "STOCK")
	private int stock;

}
