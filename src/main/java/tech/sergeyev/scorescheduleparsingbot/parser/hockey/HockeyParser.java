package tech.sergeyev.scorescheduleparsingbot.parser.hockey;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.sergeyev.scorescheduleparsingbot.parser.Parser;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class HockeyParser implements Parser {
    final Logger LOGGER = LoggerFactory.getLogger(HockeyParser.class);

    Parser clubsPageParser;
    Parser calendarPageParser;
    Parser upgradableBlockParser;
    final List<Parser> parsers = new ArrayList<>();

    @Autowired
    public HockeyParser(@Qualifier("calendarPageParser") Parser calendarPageParser,
                        @Qualifier("clubsPageParser") Parser clubsPageParser,
                        @Qualifier("upgradableBlockParser") Parser upgradableBlockParser) {
        this.clubsPageParser = clubsPageParser;
        this.calendarPageParser = calendarPageParser;
        this.upgradableBlockParser = upgradableBlockParser;
        parsers.add(clubsPageParser);
        parsers.add(calendarPageParser);
        parsers.add(upgradableBlockParser);
    }

    protected String getTextBySelector(Document document, String selector) {
        return document.select(selector).text();
    }

    protected String getTextBySelector(Document document, String selector, int i) {
        return document.select(
                selector.replace("%i%", String.valueOf(i)))
                .text();
    }

    protected String getTextBySelector(Document document, String selector, int i, int j) {
        return document.select(
                selector.replace("%i%", String.valueOf(i))
                        .replace("%j%", String.valueOf(j)))
                .text();
    }

    protected boolean hasNotNextEntry(Document document, String selector, int i) {
        return ("").equals(getTextBySelector(document, selector, i));
    }

    protected boolean hasNotNextEntry(Document document, String selector, int constantVar, int incrementedVar) {
        return ("").equals(getTextBySelector(document, selector, constantVar, incrementedVar));
    }

    @Override
    public void start() {
        LOGGER.info(this.getClass().getSimpleName() + " started");
        for (Parser parser : parsers) {
            parser.start();
        }
    }
}
