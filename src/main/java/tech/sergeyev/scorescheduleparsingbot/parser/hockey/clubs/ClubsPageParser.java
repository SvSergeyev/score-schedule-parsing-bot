package tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.model.Team;
import tech.sergeyev.scorescheduleparsingbot.parser.hockey.HockeyParser;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;
import tech.sergeyev.scorescheduleparsingbot.service.Transcriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@PropertySource("classpath:bot.properties")
public final class ClubsPageParser extends HockeyParser {
    private final Logger LOGGER = LoggerFactory.getLogger(ClubsPageParser.class);

    @Value("${url.hockey.clubs}")
    private String url;
    private final TeamService teamService;

    public ClubsPageParser(TeamService teamService) {
        this.teamService = teamService;
    }

    public void start() {
        LOGGER.info(this.getClass().getSimpleName() + " started");
        if (!teamService.teamsTableIsEmpty()) {
            LOGGER.warn("\"Teams\" table is not empty. Table will be cleared");
            teamService.dropTable();
            LOGGER.info("\"Teams\" table was cleared");
        }

        Document document = getDataFromUrl(url);

        List<String> conferences = new ArrayList<>();
        conferences.add(getConferenceFromSelector(ClubsPageSelectors.EASTERN_CONFERENCE, document));
        conferences.add(getConferenceFromSelector(ClubsPageSelectors.WESTERN_CONFERENCE, document));
        for (String conference : conferences) {
            for (int j = 2; j < 6; ) {
                for (int i = 1; i < 100; i++) {
                    boolean nextTeamIsNotAvailable;
                    Team team = new Team();
                    team.setConference(conference);
                    if (conference.equals("Восток")) {
                        team.setName(getTextBySelector(document, ClubsPageSelectors.EASTERN_NAME.getSelector(), i, j));
                        team.setCity(getTextBySelector(document, ClubsPageSelectors.EASTERN_CITY.getSelector(), i, j));
                        nextTeamIsNotAvailable = hasNotNextEntry(document, ClubsPageSelectors.EASTERN_NAME.getSelector(), i, j);
                    } else {
                        team.setName(getTextBySelector(document, ClubsPageSelectors.WESTERN_NAME.getSelector(), i, j));
                        team.setCity(getTextBySelector(document, ClubsPageSelectors.WESTERN_CITY.getSelector(), i, j));
                        nextTeamIsNotAvailable = hasNotNextEntry(document, ClubsPageSelectors.WESTERN_NAME.getSelector(), i, j);
                    }
                    team.setAbbreviation(getAbbreviationByTeam(team));

                    teamService.addTeam(team);
                    LOGGER.info("Add a new Team: " + team);

                    if (nextTeamIsNotAvailable) {
                        j = j + 3;
                        break;
                    }
                }
            }
        }
    }

    private String getConferenceFromSelector(ClubsPageSelectors selector, Document document) {
        return getTextBySelector(document, selector.getSelector())
                .replace("Конференция «", "")
                .replace("»", "");
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
