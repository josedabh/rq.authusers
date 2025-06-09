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

/**
 * The Class Reward.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REWARD")
public class Reward {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/** The name. */
	@Column(name = "NAME", length = 80)
	private String name;
	
	/** The description. */
	@Column(name = "DESCRIPTION", length = 200)
	private String description;
	
	/** The points. */
	@Column(name = "POINTS")
	private int points;
	
	/** The active. */
	@Column(name = "VISIBLE")
	private boolean visible;
	
	/** The stock. */
	@Column(name = "STOCK")
	private int stock;

}
