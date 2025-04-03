package com.rq.manager.authusers.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "CHALLENGE")
public class Challenge {
	
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DIFFICULTY", nullable = false)
	private String difficulty;
	
	//Cambiar a fecha principio y fecha final
	// Luego ver si el usuario puede ver el retos una semana antes
	// O un evento donde sea instantaneo
	@Column(name = "DURATION", nullable = false)
	private LocalDateTime duration;
	
	@Column(name = "POINTS", nullable = false)
	private int points;

}
