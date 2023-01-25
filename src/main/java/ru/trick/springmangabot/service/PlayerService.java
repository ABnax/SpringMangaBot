package ru.trick.springmangabot.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.trick.springmangabot.model.Player;

import java.util.Date;

@Component
public class PlayerService {
    private final SessionFactory sessionFactory;

    @Autowired
    public PlayerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Transactional
    public void firstCreateAccount(long chatId, String firstName) {
        Session session = sessionFactory.getCurrentSession();
        session.save(new Player(chatId, firstName, 0, false));
    }

    @Transactional(readOnly = true)
    public Player takeUser(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Player.class, chatId);
    }

    @Transactional
    public void save (Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.save(player);
    }

    @Transactional
    public void update(long chatId, int balance, boolean subscription, Date time_sub_before) {
        Session session = sessionFactory.getCurrentSession();
        Player userTemp = session.get(Player.class, chatId);
        userTemp.setBalance(balance);
        userTemp.setSubscription(subscription);
        userTemp.setTime_sub_before(time_sub_before);
        session.save(userTemp);
    }
}
