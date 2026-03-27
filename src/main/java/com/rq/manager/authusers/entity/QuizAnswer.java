package com.rq.manager.authusers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class QuizAnswer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUIZ_ANSWER")
public class QuizAnswer {

    /** ID compuesto:   [questionId]-R[counterResp] Ejemplo: "Q00001-P01-R01", "Q00001-P01-R02", …. */
    @Id
    @Column(length = 14)
    private String id;

    /** The question. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private QuizQuestion question;

    /** The text. */
    @Column(nullable = false)
    private String text;

    /** The correct. */
    @Column(name = "correct", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean correct;
}
