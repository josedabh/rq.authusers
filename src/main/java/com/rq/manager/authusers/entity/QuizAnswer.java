package com.rq.manager.authusers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "QUIZANSWER")
public class QuizAnswer {

    /** The answer id. */
    @Id
    @Column(name = "ANSWER_ID")
    private String answerId;

    /** The result. */
    @Column(name = "RESULT", nullable = false)
    private String result;

    /** The is correct. */
    @Column(name = "ISCORRECT", nullable = false)
    private boolean isCorrect;

    /** The question. */
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion question;
}
