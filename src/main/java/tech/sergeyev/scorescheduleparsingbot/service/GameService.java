package tech.sergeyev.scorescheduleparsingbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.sergeyev.scorescheduleparsingbot.model.Game;
import tech.sergeyev.scorescheduleparsingbot.repository.GameRepository;

@Service
public class GameService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void addGame(Game game) {
        LOGGER.info("Save this game: " + game);
        gameRepository.save(game);
    }

    public boolean gamesTableIsEmpty() {
        return gameRepository.findGameById(1) == null;
    }
}
