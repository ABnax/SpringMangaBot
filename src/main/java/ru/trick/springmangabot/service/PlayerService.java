package ru.trick.springmangabot.service;


import org.springframework.stereotype.Component;
import ru.trick.springmangabot.model.Player;
import ru.trick.springmangabot.repository.PlayerRepository;

import java.util.Date;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player takeUser(long chatId) {
        return playerRepository.findById(chatId).get();
    }

    public void update(long chatId, int balance, boolean subscription, Date time_sub_before) {
        Player userTemp = takeUser(chatId);
        userTemp.setBalance(balance);
        userTemp.setSubscription(subscription);
        userTemp.setTime_sub_before(time_sub_before);
        playerRepository.save(userTemp);
    }

    public void firstCreateAccount(long chatId, String firstName) {
        playerRepository.save(new Player(chatId, firstName, 0, false));
    }

    public void update(Player player) {
        playerRepository.save(player);
    }
}
