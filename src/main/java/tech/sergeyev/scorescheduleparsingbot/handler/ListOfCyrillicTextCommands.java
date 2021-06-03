package tech.sergeyev.scorescheduleparsingbot.handler;

public enum ListOfCyrillicTextCommands {
    HOCKEY("хоккей"),
    FORMULA_1("формула"),
    HELP("помощь");


    private final String cyrillicText;

    ListOfCyrillicTextCommands(String cyrillicText) {
        this.cyrillicText = cyrillicText;
    }

    public String getCyrillicText() {
        return cyrillicText;
    }
}
