package tech.sergeyev.scorescheduleparsingbot.parser;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import tech.sergeyev.scorescheduleparsingbot.service.TeamService;

import java.time.LocalDate;

@PropertySource("classpath:bot.properties")
public class UpdatableBlockParser implements Parser {
    @Value("${url.hockey.calendar}")
    private String url;
    private final TeamService teamService;

    public UpdatableBlockParser(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void start() {
        Document document = getDataFromUrl(url);
        for (int i = 1; i < 100; i++) {
            LocalDate date = convertToLocalDate(
                    document.select("div:nth-child(" + i + ") > table > tbody > tr.b-matches_data_top > td.e-matches_data_center")
                    .text()
            );
//            Блок, неактуальный в межсезонье
//            if (localDate.isBefore(LocalDate.now())) {
//                break;
//            }

//            System.out.println("DATE: " + localDate);

            String home = document
                    .select("div:nth-child(" + i + ") > table > tbody > tr.b-matches_data_middle > td.e-matches_data_left")
                    .text();
            String away = document
                    .select("div:nth-child(" + i + ") > table > tbody > tr.b-matches_data_middle > td.e-matches_data_right")
                    .text();
            String currentScore= document
                    .select("div:nth-child(" + i + ") > table > tbody > tr.b-matches_data_middle > td.e-matches_data_center")
                    .text();
            String detailedScoreOrTimeOfStart = document
                    .select("div:nth-child(" + i + ") > table > tbody > tr.b-matches_data_bottom > td > em")
                    .text();
//            System.out.println("GAME: " + home + " " + currentScore +  " " + away);
//            System.out.println("DSCR: " + detailedScoreOrTimeOfStart);

            String nextDate = document
                    .select("div:nth-child(" + (i + 1) + ") > table > tbody > tr.b-matches_data_top > td.e-matches_data_center")
                    .text();
            if ("".equals(nextDate)) break;
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
