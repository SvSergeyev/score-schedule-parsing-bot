package tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClubsPageSelectors {
    EASTERN_CONFERENCE("#wrapper > div.b-content_section.m-conf-east > div.b-wide_block > div > h2"),
    EASTERN_NAME("#wrapper > div.b-content_section.m-conf-east > div.b-blocks_cover > div.b-middle_block > ul:nth-child(%j%) > li:nth-child(%i%) > dl > dd > h5 > a"),
    EASTERN_CITY("#wrapper > div.b-content_section.m-conf-east > div.b-blocks_cover > div.b-middle_block > ul:nth-child(%j%) > li:nth-child(%i%) > dl > dd > p"),
    WESTERN_CONFERENCE("#wrapper > div.b-content_section.m-conf-west.s-float_panel_start > div.b-wide_block > div > h2"),
    WESTERN_NAME("#wrapper > div.b-content_section.m-conf-west.s-float_panel_start > div.b-blocks_cover > div.b-middle_block > ul:nth-child(%j%) > li:nth-child(%i%) > dl > dd > h5 > a"),
    WESTERN_CITY("#wrapper > div.b-content_section.m-conf-west.s-float_panel_start > div.b-blocks_cover > div.b-middle_block > ul:nth-child(%j%) > li:nth-child(%i%) > dl > dd > p");

    private final String selector;
}
