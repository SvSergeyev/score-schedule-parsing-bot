package tech.sergeyev.scorescheduleparsingbot.parser.hockey.upgradable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UpgradableBlockSelectors {
    DATE("div:nth-child(%i%) > table > tbody > tr.b-matches_data_top > td.e-matches_data_center"),
    HOME("div:nth-child(%i%) > table > tbody > tr.b-matches_data_middle > td.e-matches_data_left"),
    AWAY("div:nth-child(%i%) > table > tbody > tr.b-matches_data_middle > td.e-matches_data_right"),
    CURRENT_SCORE("div:nth-child(%i%) > table > tbody > tr.b-matches_data_middle > td.e-matches_data_center"),
    DETAILED_SCORE_OR_TIME("div:nth-child(%i%) > table > tbody > tr.b-matches_data_bottom > td > em");

    private final String selector;
}
