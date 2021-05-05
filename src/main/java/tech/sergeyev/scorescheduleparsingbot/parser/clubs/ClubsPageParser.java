package tech.sergeyev.scorescheduleparsingbot.parser.clubs;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.model.Team;
import tech.sergeyev.scorescheduleparsingbot.parser.Parser;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;
import tech.sergeyev.scorescheduleparsingbot.service.Transcriptor;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:bot.properties")
public class ClubsPageParser implements Parser {
    private final Logger LOGGER = LoggerFactory.getLogger(ClubsPageParser.class);

    @Value("${url.hockey.clubs}")
    private String url;
    private final TeamService teamService;

    public ClubsPageParser(TeamService teamService) {
        this.teamService = teamService;
    }

    public void start() {
        if (!teamService.teamsTableIsEmpty()) return;

        Document document = getDataFromUrl(url);
        List<String> conferences = new ArrayList<>();
        conferences.add(getConferenceFromSelector(ClubsParserSelectors.EASTERN_CONFERENCE, document));
        conferences.add(getConferenceFromSelector(ClubsParserSelectors.WESTERN_CONFERENCE, document));

        for (String conference : conferences) {
            for (int j = 2; j < 6; ) {
                for (int i = 1; i < 100; i++) {
                    boolean nextTeamIsNotAvailableCheck;
                    Team team = new Team();
                    team.setConference(conference);
                    if (conference.equals("Восток")) {
                        team.setName(getDataFromSelector(ClubsParserSelectors.EASTERN_NAME, document, i, j));
                        team.setCity(getDataFromSelector(ClubsParserSelectors.EASTERN_CITY, document, i, j));
                        nextTeamIsNotAvailableCheck = hasNotNextEast(i, j, document);
                    } else {
                        team.setName(getDataFromSelector(ClubsParserSelectors.WESTERN_NAME, document, i, j));
                        team.setCity(getDataFromSelector(ClubsParserSelectors.WESTERN_CITY, document, i, j));
                        nextTeamIsNotAvailableCheck = hasNotNextWest(i, j, document);
                    }
                    team.setAbbreviation(getAbbreviationByTeam(team));
                    teamService.addTeam(team);
                    LOGGER.info("Add a new Team: " + team);
                    if (nextTeamIsNotAvailableCheck) {
                        j = j + 3;
                        break;
                    }
                }
            }
        }
    }

    private String getConferenceFromSelector(ClubsParserSelectors selector, Document document) {
        return document.select(selector.getSelector()).text()
                .replace("Конференция «", "")
                .replace("»", "");
    }

    private String getDataFromSelector(ClubsParserSelectors selector, Document document, int i, int j) {
        return document
                .select(selector.getSelector()
                        .replace("%j%", String.valueOf(j))
                        .replace("%i%", String.valueOf(i)))
                .text();
    }

    private boolean hasNotNextWest(int i, int j, Document document) {
        return document.select(ClubsParserSelectors.WESTERN_NAME.getSelector()
                .replace("%j%", String.valueOf(j))
                .replace("%i%", String.valueOf(i + 1)))
                .text().equals("");
    }

    private boolean hasNotNextEast(int i, int j, Document document) {
        return document.select(ClubsParserSelectors.EASTERN_NAME.getSelector()
                .replace("%j%", String.valueOf(j))
                .replace("%i%", String.valueOf(i + 1)))
                .text().equals("");
    }

    private String getAbbreviationByTeam(Team team) {
        String name = Transcriptor.transcriptCyrillicToLatin(team.getName())
                .toUpperCase()
                .replace(" ", "_")
                .replace("\u02B9", "");

        TeamsAbbreviations abbreviation = null;
        if (name.equals("DINAMO")) {
            String cityInLatin = Transcriptor.transcriptCyrillicToLatin(team.getCity().toUpperCase());
            DinamoCities city = DinamoCities.valueOf(cityInLatin);
            switch (city) {
                case MOSKVA:
                    abbreviation = TeamsAbbreviations.DINAMO_MSK;
                    break;
                case RIGA:
                    abbreviation = TeamsAbbreviations.DINAMO_R;
                    break;
                case MINSK:
                    abbreviation = TeamsAbbreviations.DINAMO_MN;
            }
        } else {
            abbreviation = TeamsAbbreviations.valueOf(name);
        }
        return abbreviation.getAbbreviation();
    }
}
