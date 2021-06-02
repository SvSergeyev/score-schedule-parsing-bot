package tech.sergeyev.scorescheduleparsingbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    Team home;

    @ManyToOne
    Team away;

    LocalDate date;
    LocalTime startTime;
    String totalScore;
    String detailScore;
    boolean isOver;

    @Override
    public String toString() {
        return date + ": " +
                home + " " + totalScore + " " + away +
                ", [" + detailScore + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return getHome().equals(game.getHome()) &&
                getAway().equals(game.getAway()) &&
                getDate().equals(game.getDate()) &&
                getDetailScore().equals(game.getDetailScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHome(), getAway(), getDate(), getTotalScore());
    }
}
