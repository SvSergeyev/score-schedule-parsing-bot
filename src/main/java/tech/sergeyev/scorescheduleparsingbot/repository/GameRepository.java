package tech.sergeyev.scorescheduleparsingbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sergeyev.scorescheduleparsingbot.model.Game;
import tech.sergeyev.scorescheduleparsingbot.model.Team;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE g.away = :team OR g.home = :team")
    List<Game> findCompletedGamesByTeam(@Param("team") Team team);

    @Query("SELECT g FROM  Game g WHERE g.id = :id")
    Game findGameById(@Param("id") int id);
}
