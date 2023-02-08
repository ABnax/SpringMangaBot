package ru.trick.springmangabot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.trick.springmangabot.model.Alert;
import ru.trick.springmangabot.repository.AlertRepository;

import java.util.List;


@Component
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }


    public void addAlert (long chatId, String nameManga) {
        Alert tempAlert = new Alert(nameManga, true, chatId);
        alertRepository.save(tempAlert);
    }

    public boolean checkAlert(Long chatId, String nameManga) {
        return alertRepository.findByChatIdAndNameManga(chatId, nameManga) != null;
    }

    public List <Alert> takeAlertForNameManga (String nameManga) {
        return alertRepository.findByNameManga(nameManga);
    }

    public List <Alert> takeAlertForUser (long chatId) {
        return alertRepository.findByChatId(chatId);
    }

    public void deleteAlertForUser (long chatId, String nameManga){
        alertRepository.delete(alertRepository.findByChatIdAndNameManga(chatId, nameManga));
    }

}
