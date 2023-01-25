package ru.trick.springmangabot.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.trick.springmangabot.model.ChapterManga;

@Component
public class ChapterMangaService {

    private final SessionFactory sessionFactory;

    @Autowired
    public ChapterMangaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Transactional(readOnly = true)
    public ChapterManga getNumberManga (int numberChapter, String nameManga) {
        Session session = sessionFactory.getCurrentSession();
     ChapterManga chapterManga =  session.createQuery("select p from chaptermanga p where number="+numberChapter+"where name ="+nameManga, ChapterManga.class).getResultList().get(0);
        return chapterManga;
    }
}
