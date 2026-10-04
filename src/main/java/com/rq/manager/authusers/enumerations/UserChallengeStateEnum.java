package com.rq.manager.authusers.enumerations;

/**
 * Lifecycle states for a user's participation in a challenge.
 *
 * <pre>
 * JOINED ──► IN_PROGRESS ──► COMPLETED
 *                     │
 *                     └──► FAILED  (after max attempts without passing)
 * </pre>
 */
public enum UserChallengeStateEnum {

    /** User registered for the challenge but has not attempted it yet. */
    JOINED,

    /** User has made at least one attempt but has not yet passed. */
    IN_PROGRESS,

    /** User passed the challenge (score >= 70%). */
    COMPLETED,

    /** User exhausted all attempts without passing. */
    FAILED
}
