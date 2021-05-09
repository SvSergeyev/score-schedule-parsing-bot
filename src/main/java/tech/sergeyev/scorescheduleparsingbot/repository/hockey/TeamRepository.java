package tech.sergeyev.scorescheduleparsingbot.repository.hockey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sergeyev.scorescheduleparsingbot.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("SELECT t FROM Team t WHERE t.name = :name")
    Team findTeamByName(@Param("name") String name);

    @Query("SELECT t FROM Team t WHERE t.name = :name AND t.city = :city")
    Team findTeamByNameAndCity(@Param("name") String name, @Param("city") String city);

    @Query("SELECT t FROM Team t WHERE t.abbreviation = :abbreviation")
    Team findTeamByAbbreviation(@Param("abbreviation") String abbreviation);

    @Query("SELECT t FROM Team t WHERE t.id = :id")
    Team findById(@Param("id") int id);
}
