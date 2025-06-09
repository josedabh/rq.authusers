package com.rq.manager.authusers.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "QUIZ_VERIFICATION")
public class QuizVerification {
    
    /** ID completo: prefijo + numeric (ej. "Q00001") */
    @Id
    @Column(length = 6)
    private String id;

    @OneToOne
    @JoinColumn(name = "CHALLENGE_ID", nullable = false)
    private Challenge challenge;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuizQuestion> questions = new ArrayList<>();
}
