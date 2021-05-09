package tech.sergeyev.scorescheduleparsingbot.parser.hockey.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CalendarPageSelectors {
    DATE("#tab-calendar-last > div > div:nth-child(%i%) > b:nth-child(2)"),
    HOME("#tab-calendar-last > div > div:nth-child(%i%) > ul > li:nth-child(%j%) > dl:nth-child(1) > dd > h5 > a"),
    AWAY("#tab-calendar-last > div > div:nth-child(%i%) > ul > li:nth-child(%j%) > dl.b-details.m-club.m-rightward > dd > h5 > a"),
    SCORE_OR_TIME("#tab-calendar-last > div > div:nth-child(%i%) > ul > li:nth-child(%j%) > dl.b-score > dt > h3"),
    DETAILED_SCORE("#tab-calendar-last > div > div:nth-child(%i%) > ul > li:nth-child(%j%) > dl.b-score > dd > ul");

    private final String selector;
}
