package tech.sergeyev.scorescheduleparsingbot.parser;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.model.Game;
import tech.sergeyev.scorescheduleparsingbot.service.GameService;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@PropertySource("classpath:bot.properties")
public class CalendarPageParser implements Parser {
    private final Logger LOGGER = LoggerFactory.getLogger(CalendarPageParser.class);

    @Value("${url.hockey.calendar}")
    private String url;
    private final TeamService teamService;
    private final GameService gameService;

    public CalendarPageParser(TeamService teamService, GameService gameService) {
        this.teamService = teamService;
        this.gameService = gameService;
    }

    public void start() {
        if (!gameService.gamesTableIsEmpty()) return;

        Document document = getDataFromUrl(url);
        for (int i = 1; i < 1000; i++) {
            String dateAsString = document
                    .select("#tab-calendar-all > div > div:nth-child(" + i + ") > b:nth-child(2)")
                    .text();
            i++;
            for (int j = 1; j < 1000; j++) {
                Game game = new Game();
                String home = document
                        .select("#tab-calendar-all > div > div:nth-child(" + i + ") > ul > li:nth-child(" + j + ") > dl:nth-child(1) > dd > h5 > a")
                        .text();
                String away = document
                        .select("#tab-calendar-all > div > div:nth-child(" + i + ") > ul > li:nth-child(" + j + ") > dl.b-details.m-club.m-rightward > dd > h5 > a")
                        .text();
                String scoreOrTime = document
                        .select("#tab-calendar-all > div > div:nth-child(" + i + ") > ul > li:nth-child(" + j + ") > dl.b-score > dt > h3")
                        .text();
                String period = document
                        .select("#tab-calendar-all > div > div:nth-child(" + i + ") > ul > li:nth-child(" + j + ") > dl.b-score > dd > ul")
                        .text();
                LocalDate date = convertDate(dateAsString);
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

                if (hasNotNextGame(i, j, document)) {
                    break;
                }
            }
            if (hasNotNextGameDay(i, document)) {
                break;
            }
        }
    }

    public boolean hasNotNextGame(int i, int j, Document document) {
        j++;
        String check = document
                .select("#tab-calendar-all > div > div:nth-child(" + i + ") > ul > li:nth-child(" + j + ") > dl:nth-child(1) > dd > h5 > a")
                .text();
        return "".equals(check);
    }

    public boolean hasNotNextGameDay(int i, Document document) {
        i++;
        String check = document.select(
                "#tab-calendar-all > div > div:nth-child(" + i + ") > b:nth-child(2)")
                .text();
        return "".equals(check);
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
