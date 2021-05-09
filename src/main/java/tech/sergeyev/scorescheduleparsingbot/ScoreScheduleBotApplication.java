package tech.sergeyev.scorescheduleparsingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScoreScheduleBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreScheduleBotApplication.class, args);
	}

}
