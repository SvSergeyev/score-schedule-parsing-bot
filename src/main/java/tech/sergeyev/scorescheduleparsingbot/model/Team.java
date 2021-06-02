package tech.sergeyev.scorescheduleparsingbot.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public final class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String city;
    String name;
    String abbreviation;
    String conference;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Game> asHome = new ArrayList<>();

    @OneToMany(mappedBy = "away", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Game> asAway = new ArrayList<>();

    @Override
    public String toString() {
        return name + " (" + abbreviation + "), " + city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return getCity().equals(team.getCity()) &&
                getName().equals(team.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getName());
    }
}
