package ru.trick.springmangabot.service;


import org.springframework.stereotype.Component;
import ru.trick.springmangabot.model.PayToken;
import ru.trick.springmangabot.repository.PayTokenRepository;

@Component
public class PayTokenService {

    private final PayTokenRepository payTokenRepository;

    public PayTokenService(PayTokenRepository payTokenRepository) {
        this.payTokenRepository = payTokenRepository;
    }

    public void addToken (Long chatId, String token, Integer money) {
        PayToken payToken = new PayToken();
        payToken.setPayToken(token);
        payToken.setChatId(chatId);
        payToken.setMoney(money);
        payTokenRepository.save(payToken);
    }

    public PayToken takeToken (Long chatId) {
        return payTokenRepository.findByChatId(chatId).orElse(null);
    }

    public PayToken takeTokenCheck (Long chatId) {
        return payTokenRepository.findByChatId(chatId).orElse(null);
    }

    public void deleteToken (Long chatId) {
        PayToken deleteToken = payTokenRepository.findByChatId(chatId).get();
        payTokenRepository.delete(deleteToken);
    }



}
