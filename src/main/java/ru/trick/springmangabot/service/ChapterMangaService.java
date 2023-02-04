package ru.trick.springmangabot.service;


import org.springframework.stereotype.Component;
import ru.trick.springmangabot.model.ChapterManga;
import ru.trick.springmangabot.repository.ChanterMangaRepository;

import java.util.List;

@Component
public class ChapterMangaService {

    private final ChanterMangaRepository chanterMangaRepository;

    public ChapterMangaService(ChanterMangaRepository chanterMangaRepository) {
        this.chanterMangaRepository = chanterMangaRepository;
    }

    public ChapterManga findOne(Long id) {
        return chanterMangaRepository.findById(id).orElse(null);
    }

    public List<ChapterManga> findAll(String name) {
        return chanterMangaRepository.findByNameManga(name);
    }

    public void addManga (ChapterManga chapterManga) {
        chanterMangaRepository.save(chapterManga);
    }


}
