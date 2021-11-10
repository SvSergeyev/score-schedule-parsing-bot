package tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TeamsAbbreviations {
    KUNLUN_RED_STAR("КРС", "кун"),
    SIBIR("СИБ", "сиб"),
    SALAVAT_YULAYEV("СЮЛ", "сал"),
    BARYS("БАР", "бар"),
    AVTOMOBILIST("АВТ", "авт"),
    AMUR("АМР", "аму"),
    AVANGARD("АВГ", "ава"),
    TRAKTOR("ТРК", "тра"),
    TORPEDO("ТОР", "тор"),
    NEFTEKHIMIK("НХК", "неф"),
    METALLURG("ММГ", "мет"),
    AK_BARS("АКБ", "акб"),
    TSSKA("ЦСК", "цск"),
    LOKOMOTIV("ЛОК", "лок"),
    DINAMO_R("ДРГ", "дин"),
    DINAMO_MSK("ДИН", "дин"),
    DINAMO_MN("ДМН", "дин"),
    SPARTAK("СПР", "спа"),
    SOCHI("СОЧ", "соч"),
    SKA("СКА", "ска"),
    SEVERSTAL("СЕВ", "сев"),
    YOKERIT("ЙОК", "йок"),
    VITYAZ("ВИТ", "вит");

    String abbreviation;
    String keyName;
}
