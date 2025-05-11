package com.rq.manager.authusers.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizVerification.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUIZVERIFICATION")
public class QuizVerification {
    
    /** The id. */
    @Id
    @Column(name = "ID")
    private String id;
    
    /** The questions. */
    @OneToMany(mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<QuizQuestion> questions;
    
}
