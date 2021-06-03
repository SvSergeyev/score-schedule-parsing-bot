package tech.sergeyev.scorescheduleparsingbot.parser.hockey.upgradable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.model.Game;
import tech.sergeyev.scorescheduleparsingbot.model.Team;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.HockeyParser;
import tech.sergeyev.scorescheduleparsingbot.service.GameService;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;

import java.time.LocalDate;
import java.util.Objects;

@Service
@PropertySource("classpath:bot.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class UpgradableBlockParser extends HockeyParser {
    final Logger LOGGER = LoggerFactory.getLogger(UpgradableBlockParser.class);

    @Value("${url.hockey.calendar}")
    String url;
    final TeamService teamService;
    final GameService gameService;

    @Autowired
    public UpgradableBlockParser(TeamService teamService,
                                 GameService gameService) {
        this.teamService = teamService;
        this.gameService = gameService;
    }

    @Scheduled(fixedRateString = "${update.period}")
    @Override
    public void start() {
        LOGGER.info(this.getClass().getSimpleName() + " started");
        Document document = getDataFromUrl(url);
        for (int i = 1; i < 100; i++) {
            LocalDate date = convertToLocalDate(
                    getTextBySelector(document, UpgradableBlockSelectors.DATE.getSelector(), i));
//            Блок, неактуальный в межсезонье
//            if (localDate.isBefore(LocalDate.now())) {
//                break;
//            }
            Team home = teamService.getTeamByAbbreviation(
                    getTextBySelector(document, UpgradableBlockSelectors.HOME.getSelector(), i));
            Team away = teamService.getTeamByAbbreviation(
                    getTextBySelector(document, UpgradableBlockSelectors.AWAY.getSelector(), i));
            String currentScore = getTextBySelector(
                    document, UpgradableBlockSelectors.CURRENT_SCORE.getSelector(), i);
            String detailedScoreOrTimeOfStart = getTextBySelector(
                    document, UpgradableBlockSelectors.DETAILED_SCORE_OR_TIME.getSelector(), i);

            Game gameWithUpdate = new Game();
            gameWithUpdate.setDate(Objects.requireNonNull(date));
            gameWithUpdate.setHome(home);
            gameWithUpdate.setAway(away);
            gameWithUpdate.setTotalScore(currentScore);
            gameWithUpdate.setDetailScore(detailedScoreOrTimeOfStart);

            Game gameFromDatabase = gameService.getGameByTeamsAndDate(home, away, date);

            if (gameFromDatabase != null && !gameFromDatabase.equals(gameWithUpdate)) {
                gameFromDatabase.setTotalScore(currentScore);
                gameFromDatabase.setDetailScore(detailedScoreOrTimeOfStart);
                gameService.updateGame(gameFromDatabase);
            } else if (gameFromDatabase == null) {
                LOGGER.error("Game from database is null!");
            } else if (gameFromDatabase.equals(gameWithUpdate)) {
                LOGGER.info("Games is equal, no updates");
            }

            boolean nextDateIsNotAvailable = hasNotNextEntry(document, UpgradableBlockSelectors.DATE.getSelector(), i);
            if (nextDateIsNotAvailable) break;
        }
    }

    LocalDate convertToLocalDate(String dateAsText) {
        String[] shortNamesOfMonths = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        String[] dateAsArray = dateAsText.replace(",", "").split(" ");
        int day = Integer.parseInt(dateAsArray[0]);
        int month = -1;
        for (int i = 1; i < shortNamesOfMonths.length; i++) {
            if (shortNamesOfMonths[i - 1].equals(dateAsArray[1])) month = i;
        }
        return LocalDate.of(LocalDate.now().getYear(), month, day);
    }
}
