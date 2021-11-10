package tech.sergeyev.scorescheduleparsingbot.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public final class UserSubscription {
    @Id
    long chatId;

    boolean notified;


    Team team;

}
