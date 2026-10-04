package com.rq.manager.authusers.repository.entity;

import com.rq.manager.authusers.util.TsidUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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

	/** The id (TSID — time-sorted Long). */
	@Id
	@Column(name = "ID")
	private Long id;

	/** The name. */
	@Column(name = "NAME", length = 80)
	private String name;

	/** The description. */
	@Column(name = "DESCRIPTION", length = 200)
	private String description;

	/** The points. */
	@Column(name = "POINTS")
	private int points;

	/** The visible. */
	@Column(name = "VISIBLE")
	private boolean visible;

	/** The stock. */
	@Column(name = "STOCK")
	private int stock;

	@PrePersist
	public void generateId() {
		if (this.id == null) {
			this.id = TsidUtil.generate();
		}
	}
}

