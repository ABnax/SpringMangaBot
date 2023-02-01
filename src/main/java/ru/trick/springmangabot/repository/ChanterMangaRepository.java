package ru.trick.springmangabot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.trick.springmangabot.model.ChapterManga;

import java.util.List;

public interface ChanterMangaRepository extends CrudRepository<ChapterManga, Long> {
    List<ChapterManga> findByNameManga(String name);
}
