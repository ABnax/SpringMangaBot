package ru.trick.springmangabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trick.springmangabot.model.PayToken;
import ru.trick.springmangabot.model.Player;

import java.util.Optional;

public interface PayTokenRepository extends CrudRepository<PayToken, Long> {
    Optional <PayToken> findByChatId (Long chatId);
}
