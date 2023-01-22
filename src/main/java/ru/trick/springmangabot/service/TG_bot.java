package ru.trick.springmangabot.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.trick.springmangabot.model.Player;
import ru.trick.springmangabot.config.BotConfig;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboardMaker;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;
import ru.trick.springmangabot.userDAO.PlayerDAO;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@PropertySource("classpath:hibernate.properties")
public class TG_bot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final PlayerDAO userDAO;

    @Autowired
    public TG_bot(BotConfig botConfig, ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboardMaker inlineKeyboardMaker, PlayerDAO userDAO) {
        this.botConfig = botConfig;
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.userDAO = userDAO;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private boolean helpStatus = false;
    private boolean breakSwitch=true;
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String firstNameUser = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();
            String nickName = update.getMessage().getChat().getUserName();

           Integer mes = update.getMessage().getMessageId();


            Pattern pattern = Pattern.compile("[ОоТтВвЕе] [1-90]+ [А-Яа-я1-9_*:^#$!(+=0/]+");
            Matcher matcher = pattern.matcher(messageText);


            if(chatId == 1088235267 && matcher.find()) {

                char [] chars = messageText.toCharArray();
                String chatIdReturnString;

                StringBuilder chatIdOtveta = new StringBuilder();
                StringBuilder textOtveta = new StringBuilder();

                boolean work = true;
                int i = 6;
                while (work) {

                    if(chars[i]!=chars[5]){
                        chatIdOtveta.append(chars[i]);
                        i++;
                    }
                    else {work = false;
                    }
                }
                for (int j = i++; j < messageText.length(); j++) {
                    textOtveta.append((chars[j]));
                }
                String textRetrun = textOtveta.toString();

                chatIdReturnString = chatIdOtveta.toString();
                long resultId = Long.parseLong(chatIdReturnString);


                sendMessage(resultId, textRetrun);
                breakSwitch = false;
            }

        if(breakSwitch) {
            if(!helpStatus) {
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    userDAO.firstCreateAccount(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "Профиль":
                    myProfile(chatId);
                    break;

                case "Каталог":
                    String textCatalog = "Выберите мангу для чтения:";
                    try {
                        sendApiMethod(sendMessageMenuForCatalog(chatId,textCatalog));
                    } catch (TelegramApiException e) {
                        System.out.println("Пиздец");
                        throw new RuntimeException(e);     }
                    sendMessage(chatId, "Тут будет другая клавиатура + картинки с манхвой и так далее...");
                    break;
                case "Подписка":
                    subscription(chatId);
                    break;
                case "Главное меню":
                    majorMenuReturn(chatId);
                    break;
                case "Закладки":
                        sendMessage(chatId, "В процессе работы...");

                    break;
                case "Пополнить баланс":
                        sendMessage(chatId, "Временно не доступно.");
                    break;
                case "Купить подписку":
                    buySub(chatId);
                    break;
                case "Помощь":
                    helpStatus = true;
                    sendMessage(chatId, "Режим помощи включен." +
                            "\nОстольной функционал бота временно не доступен." +
                            "\nОпишите Вашу проблему.");
                    try {
                        sendApiMethod(sendMessageMenuForHelp(chatId, "Для того, чтобы вернуться назад, нажмите кнопку <Главное меню> "));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;


                default:
                    sendMessage(chatId, "Извините, такой команды нет.");
                    majorMenuReturn(chatId);
                }
          }
            if (helpStatus) {
                switch (messageText) {
                    case "Помощь":

                        break;
                    case "Главное меню":
                        helpStatus = false;
                        try {
                            sendApiMethod(sendMessageMenu(chatId, "Вы вернулись в главное меню"));
                        } catch (TelegramApiException e) {
                            System.out.println("send APi Method " + e.getMessage());
                        }
                        break;
                    default:
                        sendMessage(1088235267, "Вам пишет: "+firstNameUser+ " " +"@"+nickName + "\nID: " + chatId
                                + "\n" + messageText);

//                        try {
//                            sendApiMethod(sendMessageMenuForHelp(chatId, "Главное меню"));
//                        } catch (TelegramApiException e) {
//                            throw new RuntimeException(e);
//                        }
                }
            }
        }
        breakSwitch=true;
          }
    }
    private void majorMenuReturn(long chatId) {
        try {
            sendApiMethod(sendMessageMenu(chatId, "Вы вернулись в главное меню"));
        } catch (TelegramApiException e) {
            System.out.println("send APi Method " + e.getMessage());
        }
    }
    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет,  " + name + '\n' + "Я бот-библиотекарь!";
        log.info("Replied user: " + name);

        try {
            sendApiMethod(sendMessageMenu(chatId, answer));
        } catch (TelegramApiException e) {
            System.out.println("send APi Method " + e.getMessage());
        }
    }

    private void myProfile(long chatId) {
        Player userTemp = userDAO.takeUser(chatId);
        String answer;
        String statusSub = "не активирована";

        if (userTemp.isSubscription()) {
            statusSub = "Активна";
        }

        if(userTemp.getTime_sub_before() == null) {
            answer = "Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n' +
                "  Статус подписки: " + statusSub + '\n'+ "Стоимость подписки: 250 руб."+ "\n  Баланс: " + userTemp.getBalance();
        }else {
            answer = "Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n' +
                    "  Статус подписки: " + statusSub +  '\n' + "  Подписка активна до: " + userTemp.getTime_sub_before() +"\n  Баланс: " + userTemp.getBalance();
        }

        try {
            sendApiMethod(sendMessageMenuProfiel(chatId, answer));
        } catch (TelegramApiException e) {
            System.out.println("send APi Method " + e.getMessage());
        }

    }


    public void buySub (long chatId) {
        Player userTemp = userDAO.takeUser(chatId);
        if(userTemp.getBalance()>=250) {
            userTemp.setBalance(userTemp.getBalance() - 250);
            if (userTemp.getBalance() < 0) {
                throw new ArithmeticException("Баланс ушел в минус! Что бл***!");
            }
            if (userTemp.getTime_sub_before() == null) {
                userTemp.setSubscription(true);
                int balanceTemp = userTemp.getBalance();
                boolean status = userTemp.isSubscription();
                long now = new Date().getTime();
                long befor = 2678400000L;
                long result = now + befor;
                userTemp.setTime_sub_before((new Date(result)));

                userDAO.update(chatId, balanceTemp, status, userTemp.getTime_sub_before());
                sendMessage(chatId, "Подписка куплена! \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
            } else if (userTemp.getTime_sub_before() != null) {
                long dataEndSub = userTemp.getTime_sub_before().getTime();
                long mounth = 2678400000L;
                long result = dataEndSub + mounth;
                userTemp.getTime_sub_before().setTime(result);
                userDAO.update(chatId, userTemp.getBalance(), userTemp.isSubscription(), userTemp.getTime_sub_before());
                sendMessage(chatId, "Подписка продлена еще на месяц! \nСпасибо за покупку.\n" + "Ваш баланс: " + userTemp.getBalance());
            } else if (userTemp.getBalance() < 250) {
                sendMessage(chatId, "Недостаточно средств на балансе.");
            }
        }
    }

    private void subscription(long chatId) {
        Player userTemp = userDAO.takeUser(chatId);

        String statusSub = "не активирована";

        if (userTemp.isSubscription()) {
            statusSub = "активна";

            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "Подписка активна до: " + userTemp.getTime_sub_before() ;
            try {
                sendApiMethod(sendMessageMenuSub(chatId, answer));} catch (TelegramApiException e) {System.out.println("send APi Method " + e.getMessage());}
        } else {
            String answer = "Статус Вашей подписки: " + '\n' + statusSub + '\n' + "Стоимость подписки: 250 руб." + '\n' + "* Подписка покупается на 31 день от момента покупки.";
            try {
                sendApiMethod(sendMessageMenuSub(chatId, answer));
            } catch (TelegramApiException e) {
                System.out.println("send APi Method " + e.getMessage());
            }
        }
    }


    private SendMessage sendMessageMenuNumber2(long chatId, String textSend){

        SendMessage sendMessage = new SendMessage( String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons("START PREF", false));

        return sendMessage;
}
    private SendMessage sendMessageMenuSub (long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub());
        return sendMessage;
    }
    private SendMessage sendMessageMenuProfiel (long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForProfiel());
        return sendMessage;
    }
    private SendMessage sendMessageMenuForHelp (long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenu());
        return sendMessage;
    }
    private SendMessage sendMessageMenu (long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage sendMessageMenuForCatalog (long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainTempKeybord());
        return sendMessage;
    }

    private void sendMessage (long chatId, String textSend)  {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("error occurred: " + e.getMessage());
        }

    }

//    private SendMessage testMenu (long chatId, String textSend) {
//        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
//        sendMessage.setReplyMarkup(getInlineMessageButtons());
//        return sendMessage;
//    }
//private InlineKeyboardMarkup getInlineMessageButtons(){
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton buttonYes = new InlineKeyboardButton("buttonYes");
//        InlineKeyboardButton buttonNo = new InlineKeyboardButton("buttonNo");
//        InlineKeyboardButton buttonTEXT1 = new InlineKeyboardButton("text1");
//        InlineKeyboardButton buttonTEXT2 = new InlineKeyboardButton("text2");
//
//        buttonYes.setCallbackData("buttonYes");
//        buttonNo.setCallbackData("buttonNo");
//        buttonTEXT1.setCallbackData("text1");
//        buttonTEXT2.setCallbackData("text2");
//
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//            keyboardButtonsRow1.add(buttonYes);
//            keyboardButtonsRow1.add(buttonNo);
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//            keyboardButtonsRow2.add(buttonTEXT1);
//            keyboardButtonsRow2.add(buttonTEXT2);
//
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//            rowList.add(keyboardButtonsRow1);
//            rowList.add(keyboardButtonsRow2);
//
//        inlineKeyboardMarkup.setKeyboard(rowList);
//        return inlineKeyboardMarkup;
//}
//
//private BotApiMethod<?> processCallbackQuery (CallbackQuery buttonQuery){
//        final long chatId = buttonQuery.getMessage().getChatId();
//        final long userId = buttonQuery.getFrom().getId();
//     BotApiMethod<?> callBackAnswer = sendMessageMenu(chatId, "text1231231231");
//
//
//     if(buttonQuery.getData().equals("buttonYes")) {
//         callBackAnswer = new SendMessage(String.valueOf(chatId), "yes");
//         sendMessage(1088235267,"test texxt from method");
//          } else if (buttonQuery.getData().equals("buttonNo")) {
//         callBackAnswer = new SendMessage(String.valueOf(chatId), "no");
//     } else if(buttonQuery.getData().equals("buttonTEXT1")) {
//      callBackAnswer = sendAnswerCallbackQuery ("Текст фор 1 техт",true, buttonQuery);
//     } else if(buttonQuery.getData().equals("buttonTEXT2")) {
//             callBackAnswer = sendAnswerCallbackQuery ("Текст фор 2 техт",false, buttonQuery);
//  }
//
//            return callBackAnswer;
//    }
//
//
//    private AnswerCallbackQuery sendAnswerCallbackQuery (String text, boolean alert, CallbackQuery callbackQuery){
//        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
//        answerCallbackQuery.setShowAlert(alert);
//        answerCallbackQuery.setText(text);
//        return answerCallbackQuery;
//    }

}



