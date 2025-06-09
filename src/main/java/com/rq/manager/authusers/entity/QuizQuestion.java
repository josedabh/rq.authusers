package com.rq.manager.authusers.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizQuestion.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUIZ_QUESTION")
public class QuizQuestion {
	
    /**
     * ID compuesto:
     *   [quizId]-P[counterPreg]
     * Ejemplo: "Q00001-P01", "Q00001-P02", …
     */
    @Id
    @Column(length = 10)
    private String id;

    @ManyToOne
    @JoinColumn(name = "QUIZ_ID", nullable = false)
    private QuizVerification quiz;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizAnswer> answers = new ArrayList<>();
}
