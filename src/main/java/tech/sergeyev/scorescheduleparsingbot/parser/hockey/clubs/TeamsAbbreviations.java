package tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeamsAbbreviations {
    KUNLUN_RED_STAR("КРС"),
    SIBIR("СИБ"),
    SALAVAT_YULAYEV("СЮЛ"),
    BARYS("БАР"),
    AVTOMOBILIST("АВТ"),
    AMUR("АМР"),
    AVANGARD("АВГ"),
    TRAKTOR("ТРК"),
    TORPEDO("ТОР"),
    NEFTEKHIMIK("НХК"),
    METALLURG("ММГ"),
    AK_BARS("АКБ"),
    TSSKA("ЦСК"),
    LOKOMOTIV("ЛОК"),
    DINAMO_R("ДРГ"),
    DINAMO_MSK("ДИН"),
    DINAMO_MN("ДМН"),
    SPARTAK("СПР"),
    SOCHI("СОЧ"),
    SKA("СКА"),
    SEVERSTAL("СЕВ"),
    YOKERIT("ЙОК"),
    VITYAZ("ВИТ");

    String abbreviation;
}
