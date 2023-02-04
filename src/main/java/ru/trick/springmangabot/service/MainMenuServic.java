package ru.trick.springmangabot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;
import ru.trick.springmangabot.model.ChapterManga;
import ru.trick.springmangabot.model.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class MainMenuServic {

    private final PlayerService playerService;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final KeyboardServic keyboardServic;

    private final ChapterMangaService chapterMangaService;

    @Autowired
    public MainMenuServic(PlayerService playerService, ReplyKeyboardMaker replyKeyboardMaker, KeyboardServic keyboardServic, ChapterMangaService chapterMangaService) {
        this.playerService = playerService;
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.keyboardServic = keyboardServic;
        this.chapterMangaService = chapterMangaService;
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

    public SendMessage returnMessageHelp(String messageText) {
        String[] words = messageText.split("\\s");
        StringBuilder textRetrun = new StringBuilder();
        for (int i = 2; i < words.length; i++) {
            textRetrun.append(words[i])
                    .append(" ");
        }
        return sendMessage(Long.parseLong(words[1]), "\uD83D\uDED1 Ответ от поддержки: " + textRetrun.toString());
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
        String text;
        String statusSub = " ❌ ";

        if (userTemp.isSubscription()) {
            statusSub = "✅";
        }

        if (userTemp.getTime_sub_before() == null) {
            text = "\uD83D\uDCBC Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n' +
                    "  Статус подписки: " + statusSub + '\n' + "* Стоимость подписки: 250 руб." + "\n  Баланс: " + userTemp.getBalance();
        } else {
            text = "\uD83D\uDCBC Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n'
                    + "  Статус подписки: " + statusSub + '\n' + "  Подписка активна до: " + userTemp.getTime_sub_before()
                    + "\n  Баланс: " + userTemp.getBalance();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForProfiel());

        return sendMessage;
    }

    public SendMessage buySub(long chatId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Недостаточно средств на балансе.");
        sendMessage.setChatId(chatId);

        Player userTemp = playerService.takeUser(chatId);
        if (userTemp.getBalance() >= 250) {
            userTemp.setBalance(userTemp.getBalance() - 250);
            if (userTemp.getBalance() < 0) {
                throw new ArithmeticException("Баланс ушел в минус! Ощибка!");
            }

            if (userTemp.getTime_sub_before() == null) {
                userTemp.setSubscription(true);
                userTemp.setTime_sub_before(new Date(new Date().getTime() + 2678400000L));
                String stringDate = dateFormat.format(userTemp.getTime_sub_before());
                sendMessage.setText("Подписка куплена!" + "\nПодписка активна до: " + stringDate + " \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
                playerService.update(chatId, userTemp.getBalance(), userTemp.isSubscription(), userTemp.getTime_sub_before());
            } else if (userTemp.getTime_sub_before() != null) {
                userTemp.getTime_sub_before().setTime(userTemp.getTime_sub_before().getTime() + 2678400000L);
                String stringDate = dateFormat.format(userTemp.getTime_sub_before());
                playerService.update(chatId, userTemp.getBalance(), userTemp.isSubscription(), userTemp.getTime_sub_before());
                sendMessage.setText("Подписка продлена на месяц!" + "\nПодписка активна до: " + stringDate + " \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
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
                "\nОпишите Вашу проблему." +
                "\nХотим предупредить за спам в этот чат, Вам может быть выдан бан.");
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenu());
        return sendMessage;
    }

    public SendMessage subscription(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Player userTemp = playerService.takeUser(chatId);
        String statusSub = " ❌ ";

        if (userTemp.isSubscription()) {
            statusSub = "✅";

            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "Подписка активна до: " + userTemp.getTime_sub_before();
            sendMessage.setText(answer);
        } else {
            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "* Стоимость подписки: 250 руб." + '\n' + "* Подписка покупается на 31 день от момента покупки.";
            sendMessage.setText(answer);
        }
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub());
        return sendMessage;
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

    public SendMessage banForHelp (String messageText) {
        String[] words = messageText.split("\\s");

        Player temp = playerService.takeUser(Long.parseLong(words[1]));
        temp.setBanStatus(true);
        playerService.update(temp);

        return sendMessage(Long.parseLong(words[1]), "\uD83D\uDED1 Сообщение от администрации: " + "\nВам был выдан бан на использование кнопки \"помощь\". ");
    }

    public SendMessage unBanForHelp(String messageText) {
        String[] words = messageText.split("\\s");
        Player temp = playerService.takeUser(Long.parseLong(words[1]));
        temp.setBanStatus(false);
        playerService.update(temp);
        return sendMessage(Long.parseLong(words[1]), "\uD83D\uDED1 Сообщение от администрации: " + "\nВы были разблокированы.");
    }


    public SendMessage mangaAdd(String messageText, Update update) {
                                                        //        0       1       2     3
                                                       //     добавить Emperor   455   url

        String[] words = messageText.split("\\s");
                                                       //      int number,       String url, String name
        ChapterManga newChapterManga = new ChapterManga(Integer.parseInt(words[2]),words[3], words[1]);
        chapterMangaService.addManga(newChapterManga);

        return sendMessage(update.getMessage().getChatId(), "\uD83D\uDED1 Глава №" + words[2] +" была добавлена." );
    }


    public SendMessage getListForName (String messageText, long chatId) {
        String[] words = messageText.split("\\s");
        List<ChapterManga> listManga = chapterMangaService.findAll(words[1]);
        StringBuilder stringBuilder = new StringBuilder();

        for (ChapterManga list : listManga) {
            stringBuilder.append("ID: ")
                    .append(list.getId())
                    .append("  //  Номер главы: ")
                    .append(list.getNumber())
                    .append(" // ")
                    .append(list.getNameManga())
                    .append('\n')
                    .append("Url:  ")
                    .append(list.getUrl())
                    .append('\n');
        }

        return sendMessage(chatId, stringBuilder.toString());
    }


}

