package com.rq.manager.authusers.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "QUIZQUESTION")
public class QuizQuestion {

    /** The question id. */
    private String questionId;
    
    /** The question. */
    private String question;
    
    /** The quiz. */
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    QuizVerification quiz;

    /** The answers. */
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL, orphanRemoval=true)
    List<QuizAnswer> answers;
}
