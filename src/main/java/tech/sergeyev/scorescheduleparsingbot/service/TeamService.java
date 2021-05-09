package tech.sergeyev.scorescheduleparsingbot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.sergeyev.scorescheduleparsingbot.model.Team;
import tech.sergeyev.scorescheduleparsingbot.repository.hockey.TeamRepository;

@Service
public class TeamService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public Team getTeamByName(String name) {
        String[] nameAsArray;
        if (name.contains(" ")) {
            nameAsArray = name.split(" ");
            if (nameAsArray[0].equals("Динамо")) {
                name = nameAsArray[0];
                switch (nameAsArray[1]) {
                    case ("М"): return teamRepository.findTeamByNameAndCity(name, "Москва");
                    case ("Р"): return teamRepository.findTeamByNameAndCity(name, "Рига");
                    case ("Мн"): return teamRepository.findTeamByNameAndCity(name, "Минск");
                }
            }
            switch (nameAsArray[1]) {
                case ("Мг"):
                case("НН"):
                    name = nameAsArray[0];
            }
        }
        return teamRepository.findTeamByName(name);
    }

    public boolean teamsTableIsEmpty() {
        return teamRepository.findById(1) == null;
    }

    public Team getTeamByAbbreviation(String abbreviation) {
        return teamRepository.findTeamByAbbreviation(abbreviation);
    }

    @Transactional
    public void dropTable() {
        teamRepository.deleteAll();
    }
}
