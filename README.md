## Java Telegram webhook bot
### Идея
Создать webhook-бота для получения с различных сайтов спортивных
лиг и соревнований результатов и расписаний для выбранной
пользователем команды

### Краткое описание работы
В ответ на стандартное приветственное сообщение **/start**
бот предлагает выбрать вид спорта и затем соответствующую
команду. Пользователю становится доступно меню с возможностью
получения:

* списка из результатов трех прошедших игр/гонок и т.п.;
* списка из даты и времени начала трех ближайших соревнований.

Для доступа к меню пользователь может пользоваться как
inlineKeyboard клавиатурой, прикрепленной к каждому 
сообщению, так и текстовыми командами в формате **/команда** 

### Используемые технологии
Java 14, Maven, Lombok, Spring: Boot, REST, JPA; TelegramBots API, 
Jsoup, Postgres SQL

### Текущее состояние разработки
На указанное число реализованы следующие элементы:
#### 03.06.2021
* Основной класс [Bot](https://github.com/SvSergeyev/score-schedule-parsing-bot/blob/master/src/main/java/tech/sergeyev/scorescheduleparsingbot/bot/Bot.java) 
* [Парсеры](https://github.com/SvSergeyev/score-schedule-parsing-bot/tree/master/src/main/java/tech/sergeyev/scorescheduleparsingbot/parser/hockey) 
  для сайта **khl.ru**
* Классы сущностей
  [Game](https://github.com/SvSergeyev/score-schedule-parsing-bot/blob/master/src/main/java/tech/sergeyev/scorescheduleparsingbot/model/Game.java)
  и [Team](https://github.com/SvSergeyev/score-schedule-parsing-bot/blob/master/src/main/java/tech/sergeyev/scorescheduleparsingbot/model/Team.java)
  — для обработки хоккейных матчей
* Репозитории для получения данных из БД *(на данный момент localhost)* для 
сущностей классов Team и Game
* Служебный класс [Transcriptor](https://github.com/SvSergeyev/score-schedule-parsing-bot/blob/master/src/main/java/tech/sergeyev/scorescheduleparsingbot/service/Transcriptor.java) 
  для транслитерации кириллических и латинских символов
