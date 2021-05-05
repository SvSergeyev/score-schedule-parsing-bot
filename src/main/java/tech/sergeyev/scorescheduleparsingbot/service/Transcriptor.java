package tech.sergeyev.scorescheduleparsingbot.service;

import com.ibm.icu.text.Transliterator;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class Transcriptor {
    public static final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    public static final String LATIN_TO_CYRILLIC = "Latin-Russian/BGN";

    public static String transcriptCyrillicToLatin(String text) {
        Transliterator toLatin = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatin.transliterate(text);
    }

    public static String transcriptLatinToCyrillic(String text) {
        Transliterator toCyrillic = Transliterator.getInstance(LATIN_TO_CYRILLIC);
        return toCyrillic.transliterate(text);
    }
}
