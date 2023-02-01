package ru.trick.springmangabot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;
import ru.trick.springmangabot.model.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class MainMenuServic {

    private final PlayerService playerService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final KeyboardServic keyboardServic;

    @Autowired
    public MainMenuServic(PlayerService playerService, ReplyKeyboardMaker replyKeyboardMaker, KeyboardServic keyboardServic) {
        this.playerService = playerService;
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.keyboardServic = keyboardServic;
    }

    public SendMessage wrapperForDefaultReturnInHelo(long chatId, String nickName, String firstNameUser, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenu());

        sendMessage.setText("Вам пишет: " + firstNameUser + " " + "@" + nickName + "\nID: " + chatId + "\n" + messageText);

        return sendMessage;
    }

    public void checkSub(long chatId) {
        Player playerSub = playerService.takeUser(chatId);
        if (playerSub.getTime_sub_before() != null && playerSub.getTime_sub_before().getTime() < new Date().getTime()) {
            playerSub.setSubscription(false);
            playerSub.setTime_sub_before(null);
            playerService.update(playerSub);
        }
    }

    public void returnMessageHelp(String messageText) {
        char[] chars = messageText.toCharArray();
        String chatIdReturnString;

        StringBuilder chatIdOtveta = new StringBuilder();
        StringBuilder textOtveta = new StringBuilder();

        boolean work = true;
        int i = 6;
        while (work) {

            if (chars[i] != chars[5]) {
                chatIdOtveta.append(chars[i]);
                i++;
            } else {
                work = false;
            }
        }
        for (int j = i++; j < messageText.length(); j++) {
            textOtveta.append((chars[j]));
        }
        String textRetrun = textOtveta.toString();

        chatIdReturnString = chatIdOtveta.toString();
        long resultId = Long.parseLong(chatIdReturnString);

        sendMessage(resultId, "\uD83D\uDED1 Ответ от поддержки: " + textRetrun);
    }

    public SendMessage complitStart(long chatId, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (playerService.takeUser(chatId) == null) {
            playerService.firstCreateAccount(chatId, update.getMessage().getChat().getFirstName());
        }
        log.info("Replied user: " + update.getMessage().getChat().getFirstName());
        sendMessage.setText(startMessage(update.getMessage().getChat().getFirstName()));
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    public String startMessage(String name) {
        String answer = "Привет,  " + name + '\n' + "Я бот-библиотекарь!";
        log.info("Replied user: " + name);
        return answer;
    }

    public SendMessage myProfile(long chatId) {
        Player userTemp = playerService.takeUser(chatId);
        String answer;
        String statusSub = " ❌ ";

        if (userTemp.isSubscription()) {
            statusSub = "✅";
        }

        if (userTemp.getTime_sub_before() == null) {
            answer = "\uD83D\uDCBC Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n' +
                    "  Статус подписки: " + statusSub + '\n' + "* Стоимость подписки: 250 руб." + "\n  Баланс: " + userTemp.getBalance();
        } else {
            answer = "\uD83D\uDCBC Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n'
                    + "  Статус подписки: " + statusSub + '\n' + "  Подписка активна до: " + userTemp.getTime_sub_before()
                    + "\n  Баланс: " + userTemp.getBalance();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForProfiel());

        return sendMessage;
    }

    public SendMessage buySub(long chatId) {
        Player userTemp = playerService.takeUser(chatId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Недостаточно средств на балансе.");
        sendMessage.setChatId(chatId);
        if (userTemp.getBalance() >= 250) {
            userTemp.setBalance(userTemp.getBalance() - 250);
            if (userTemp.getBalance() < 0) {
                throw new ArithmeticException("Баланс ушел в минус! Ощибка!");
            }
            if (userTemp.getTime_sub_before() == null) {
                userTemp.setSubscription(true);
                int balanceTemp = userTemp.getBalance();
                boolean status = userTemp.isSubscription();
                userTemp.setTime_sub_before(new Date(new Date().getTime() + 2678400000L));
                String stringDate = dateFormat.format(userTemp.getTime_sub_before());
                sendMessage.setText("Подписка куплена!" + "\nПодписка активна до: " + stringDate +
                        " \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
                playerService.update(chatId, balanceTemp, status, userTemp.getTime_sub_before());
            } else if (userTemp.getTime_sub_before() != null) {
                userTemp.getTime_sub_before().setTime(userTemp.getTime_sub_before().getTime() + 2678400000L);
                String stringDate = dateFormat.format(userTemp.getTime_sub_before());
                playerService.update(chatId, userTemp.getBalance(), userTemp.isSubscription(), userTemp.getTime_sub_before());
                sendMessage.setText("Подписка продлена на месяц!" + "\nПодписка активна до: " + stringDate +
                        " \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
            } else {
                sendMessage.setText("Недостаточно средств на балансе.");
            }
        } else {
            sendMessage.setText("Недостаточно средств на балансе.");
        }
        return sendMessage;
    }

    public SendMessage helpClick(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Режим помощи включен." +
                "\nОстольной функционал бота временно не доступен." +
                "\nОпишите Вашу проблему.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenu());
        return sendMessage;
    }

    public SendMessage subscription(long chatId) {
        Player userTemp = playerService.takeUser(chatId);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String statusSub = " ❌ ";

        if (userTemp.isSubscription()) {
            statusSub = "✅";

            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "Подписка активна до: " + userTemp.getTime_sub_before();
            sendMessage.setText(answer);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub());
            return sendMessage;
        } else {
            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "* Стоимость подписки: 250 руб." + '\n' + "* Подписка покупается на 31 день от момента покупки.";
            sendMessage.setText(answer);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub());
            return sendMessage;
        }

    }

    public SendMessage sendMessage(long chatId, String textSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textSend);

        return message;
    }

    public SendMessage sendMessageForHelp(long chatId, String textSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textSend);
        message.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        return message;
    }

}

