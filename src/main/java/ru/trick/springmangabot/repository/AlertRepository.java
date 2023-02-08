package ru.trick.springmangabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trick.springmangabot.model.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends CrudRepository<Alert, Long> {

    List<Alert> findByChatId (Long chatId);

    Alert findByChatIdAndNameManga (long chatId, String name);
    List <Alert> findByNameManga (String name);


}
