package tech.sergeyev.scorescheduleparsingbot.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String city;
    String name;
    String abbreviation;
    String conference;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY)
    List<Game> asHome = new ArrayList<>();

    @OneToMany(mappedBy = "away", fetch = FetchType.LAZY)
    List<Game> asAway = new ArrayList<>();

    @Override
    public String toString() {
        return name + " (" + abbreviation + "), " + city;
    }
}
