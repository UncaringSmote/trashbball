package com.mycompany.trashbasketball.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "score")
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_score")
    private Long totalscore;

    @Column(name = "last_score")
    private Long lastScore;

    @Column(name = "last_date")
    private Instant lastScoreDate = null;

    @Size(min = 1, max = 50)
    @Column(name = "login", length = 50, unique = true, nullable = false)
    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Score setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getTotalscore() {
        return totalscore;
    }

    public Score setTotalscore(Long totalscore) {
        this.totalscore = totalscore;
        return this;
    }

    public Long getLastScore() {
        return lastScore;
    }

    public Score setLastScore(Long lastScore) {
        this.lastScore = lastScore;
        return this;
    }

    public Instant getLastScoreDate() {
        return lastScoreDate;
    }

    public Score setLastScoreDate(Instant lastScoreDate) {
        this.lastScoreDate = lastScoreDate;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Score setLogin(String login) {
        this.login = login;
        return this;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Score score = (Score) o;
        return Objects.equals(id, score.id) && Objects.equals(userId, score.userId) && Objects.equals(totalscore,
            score.totalscore) && Objects.equals(lastScore, score.lastScore) && Objects.equals(lastScoreDate,
            score.lastScoreDate) && Objects.equals(login, score.login);
    }

    @Override public int hashCode() {
        return Objects.hash(id, userId, totalscore, lastScore, lastScoreDate, login);
    }

    @Override public String toString() {
        return "Score{" + "id=" + id + ", userId=" + userId + ", totalscore=" + totalscore + ", lastScore=" + lastScore
            + ", lastScoreDate=" + lastScoreDate + ", login='" + login + '\'' + '}';
    }
}
