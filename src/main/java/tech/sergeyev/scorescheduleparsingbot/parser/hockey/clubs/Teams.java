package tech.sergeyev.scorescheduleparsingbot.parser.hockey.clubs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Teams {
    KUNLUN_RED_STAR("КРС", "krs"),
    SIBIR("СИБ", "sib"),
    SALAVAT_YULAYEV("СЮЛ", "sul"),
    BARYS("БАР", "brs"),
    AVTOMOBILIST("АВТ", "avt"),
    AMUR("АМР", "amr"),
    AVANGARD("АВГ", "avg"),
    TRAKTOR("ТРК", "trk"),
    TORPEDO("ТОР", "tor"),
    NEFTEKHIMIK("НХК", "nhk"),
    METALLURG("ММГ", "mmg"),
    AK_BARS("АКБ", "akb"),
    TSSKA("ЦСК", "csk"),
    LOKOMOTIV("ЛОК", "lok"),
    DINAMO_R("ДРГ", "drg"),
    DINAMO_MSK("ДИН", "din"),
    DINAMO_MN("ДМН", "dmn"),
    SPARTAK("СПР", "spr"),
    SOCHI("СОЧ", "soc"),
    SKA("СКА", "ska"),
    SEVERSTAL("СЕВ", "sev"),
    YOKERIT("ЙОК", "jok"),
    VITYAZ("ВИТ", "vit");

    String abbreviation;
    String callback;
}
