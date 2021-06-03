package tech.sergeyev.scorescheduleparsingbot.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface Parser {
    Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    default Document getDataFromUrl(String url) {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/bot.properties");
            property.load(fis);
        } catch (IOException e) {
            LOGGER.error("Error retrieving data from bot.property file: ", e);
        }
        String USER_AGENT = property.getProperty("url.userAgent");
        String REFERRER = property.getProperty("url.referrer");

        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .maxBodySize(Integer.MAX_VALUE)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public void start();
}
