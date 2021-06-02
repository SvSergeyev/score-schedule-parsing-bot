package tech.sergeyev.scorescheduleparsingbot.parser.hockey.calendar;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.model.Game;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.HockeyParser;
import tech.sergeyev.scorescheduleparsingbot.service.GameService;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@PropertySource("classpath:bot.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class CalendarPageParser extends HockeyParser {
    final Logger LOGGER = LoggerFactory.getLogger(CalendarPageParser.class);

    @Value("${url.hockey.calendar}")
    String url;
    final TeamService teamService;
    final GameService gameService;

    @Autowired
    public CalendarPageParser(TeamService teamService,
                              GameService gameService) {
        this.teamService = teamService;
        this.gameService = gameService;
    }

    public void start() {
        LOGGER.info(this.getClass().getSimpleName() + " started");

        if (!gameService.gamesTableIsEmpty()) {
            LOGGER.warn("\"Games\" table is not empty. Table will be cleared");
            gameService.dropTable();
            LOGGER.info("\"Games\" table was cleared");
        }

        Document document = getDataFromUrl(url);
        for (int i = 1; i < 1000; i++) {
            LocalDate date = convertDate(getTextBySelector(document, CalendarPageSelectors.DATE.getSelector(), i));
            i++;
            for (int j = 1; j < 1000; j++) {
                Game game = new Game();
                String home = getTextBySelector(document, CalendarPageSelectors.HOME.getSelector(), i, j);
                String away = getTextBySelector(document, CalendarPageSelectors.AWAY.getSelector(), i, j);
                String scoreOrTime = getTextBySelector(document, CalendarPageSelectors.SCORE_OR_TIME.getSelector(), i, j);
                String period = getTextBySelector(document, CalendarPageSelectors.DETAILED_SCORE.getSelector(), i, j);

                game.setDate(date);
                game.setHome(teamService.getTeamByName(home));
                game.setAway(teamService.getTeamByName(away));

                if (scoreOrTime.contains(":")) {
                    game.setStartTime(convertTime(scoreOrTime));
                } else {
                    game.setTotalScore(scoreOrTime);
                    game.setDetailScore(period);
                    String[] periodAsArray = period.split(" ");
                    if (periodAsArray.length >= 3) game.setOver(true);
                }

                gameService.addGame(game);
                boolean nextGameIsNotAvailable = hasNotNextEntry(document, CalendarPageSelectors.HOME.getSelector(), i, j);
                if (nextGameIsNotAvailable) {
                    LOGGER.info("End of the game day");
                    break;
                }
            }
            boolean nextDayIsNotAvailable = hasNotNextEntry(document, CalendarPageSelectors.DATE.getSelector(), i);
            if (nextDayIsNotAvailable) {
                LOGGER.info("End of game list");
                break;
            }
        }
    }

    private LocalDate convertDate(String dateAsString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, E");
        return LocalDate.parse(dateAsString, formatter);
    }

    private LocalTime convertTime(String timeAsText) {
        timeAsText = timeAsText.replace(" мск", "");
        String[] timeAsArray = timeAsText.split(":");
        int hour = Integer.parseInt(timeAsArray[0]);
        int min = Integer.parseInt(timeAsArray[1]);
        return LocalTime.of(hour, min);
    }
}
