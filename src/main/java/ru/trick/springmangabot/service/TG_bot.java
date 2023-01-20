package ru.trick.springmangabot.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.trick.springmangabot.model.User;
import ru.trick.springmangabot.config.BotConfig;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboardMaker;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;
import ru.trick.springmangabot.userDAO.UserDAO;

@Slf4j
@Component
@PropertySource("classpath:hibernate.properties")
public class TG_bot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final UserDAO userDAO;

    @Autowired
    public TG_bot(BotConfig botConfig, ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboardMaker inlineKeyboardMaker, UserDAO userDAO) {
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
//    private boolean adminStatus=false;
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String firstNameUser = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();


//            if(chatId == 1088235267 && adminStatus) {
//                Pattern pattern = Pattern.compile("[Овет] [А-Яа-я1-9_*+=0/]+");
//                if(pattern.matcher(messageText));
//                switch (pattern.matcher(messageText)) {
//                    case true:
//                        break;
//                }
//            }

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
                    break;
                case "Подписка":
                    subscription(chatId);
                    break;
                case "Главное меню":
                    majorMenuReturn(chatId);
                    break;
                case "Закладки":

                    break;
                case "Пополнить баланс":
                        sendMessage(chatId, "Пока не работает....");
                    break;
                case "Назад":
                 //   majorMenuReturn(chatId);
                    ////////////////////////////////////////
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
                        sendApiMethod(sendMessageMenuForHelp(chatId, "Для того, чтобы вернуться надаз, нажмите кнопку <Главное меню> "));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;

//                case "/Admin Ответ":
//                    if(chatId == 1088235267){
//                    sendMessage(chatId, "Включен доступ для ответа");
//                    adminStatus = true;
//                    }
//                    break;

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
                        sendMessage(1088235267, "Вам пишет: "+firstNameUser + "\nID: " + chatId
                                + "\nСообщение пользователя: " + messageText);

//                        try {
//                            sendApiMethod(sendMessageMenuForHelp(chatId, "Главное меню"));
//                        } catch (TelegramApiException e) {
//                            throw new RuntimeException(e);
//                        }
                }
            }
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
//    @Transactional
//    public void firstCreateAccount(long chatId, String firstName) {
//        Session session = sessionFactory.getCurrentSession();
//       session.save(new User (chatId, firstName, 0, false));
//    }
//    @Transactional
//    public User takeUser(long chatId) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(User.class, chatId);
//    }

    private void myProfile(long chatId) {
        User userTemp = userDAO.takeUser(chatId);

        String statusSub = "не активирована";
        if (userTemp.isSubscription()) {
            statusSub = "активна";
        }
        String answer = "Ваш профиль: \n  Ваше имя: " + userTemp.getName() + '\n' + "  ID: " + userTemp.getId() + '\n' +
                "  Статус подписки: " + statusSub + "\n  Баланс: " + userTemp.getBalance();
        try {
            sendApiMethod(sendMessageMenuProfiel(chatId, answer));
        } catch (TelegramApiException e) {
            System.out.println("send APi Method " + e.getMessage());
        }

    }


    public void buySub (long chatId) {
        User userTemp = userDAO.takeUser(chatId);
        if(!userTemp.isSubscription() && userTemp.getBalance()>=150) {
            userTemp.setBalance(userTemp.getBalance()-150);
            if(userTemp.getBalance()<0) {
                throw new ArithmeticException("Баланс ушел в минус! Что бл***!");
            }
         userTemp.setSubscription(true);
            int balanceTemp = userTemp.getBalance();
            boolean status = userTemp.isSubscription();
            // Это первый вариант (Затратный, уже запрос есть его можно просто сохранить...)
            userDAO.update(chatId,balanceTemp,status);
            sendMessage(chatId, "Подписка куплена! \nСпасибо за покупку.\n"+ "Ваш баланс: "+userTemp.getBalance());
        } else if (userTemp.isSubscription()) { sendMessage(chatId, "Извините, у Вас уже есть подписка.");}
          else if (userTemp.getBalance()<150) {sendMessage(chatId, "Недостаточно средств на балансе.");}
    }

    private void subscription(long chatId) {
        User userTemp = userDAO.takeUser(chatId);

        String statusSub = "не активирована";

        if (userTemp.isSubscription()) {
            statusSub = "активна";


            String answer = "Статус Вашей подписки: " + statusSub + '\n' + "Подписка активна до: " + "**/**/20**";
            try {
                sendApiMethod(sendMessageMenuSub(chatId, answer));} catch (TelegramApiException e) {System.out.println("send APi Method " + e.getMessage());}
        } else {
            String answer = "Статус Вашей подписки: " + '\n' + statusSub;
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

}

