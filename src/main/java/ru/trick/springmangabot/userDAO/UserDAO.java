package ru.trick.springmangabot.userDAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.trick.springmangabot.model.User;

@Component
public class UserDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void firstCreateAccount(long chatId, String firstName) {
        Session session = sessionFactory.getCurrentSession();
        session.save(new User(chatId, firstName, 0, false));
    }

    @Transactional(readOnly = true)
    public User takeUser(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, chatId);
    }

    @Transactional
    public void update (long chatId, int balance, boolean subscription){
        Session session = sessionFactory.getCurrentSession();
        User userTemp = session.get(User.class, chatId);
        userTemp.setBalance(balance);
        userTemp.setSubscription(subscription);
        session.save(userTemp);
    }

}
