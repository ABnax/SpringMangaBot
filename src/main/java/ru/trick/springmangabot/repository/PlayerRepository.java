package ru.trick.springmangabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trick.springmangabot.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
