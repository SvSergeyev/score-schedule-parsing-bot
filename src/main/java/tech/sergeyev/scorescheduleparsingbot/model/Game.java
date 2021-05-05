package tech.sergeyev.scorescheduleparsingbot.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Game {
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
        return "Game{" +
                "home=" + home +
                ", away=" + away +
                ", date=" + date +
                ", totalScore='" + totalScore + '\'' +
                ", detailScore='" + detailScore + '\'' +
                '}';
    }
}
