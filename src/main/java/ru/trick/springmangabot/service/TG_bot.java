package ru.trick.springmangabot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.trick.springmangabot.config.BotConfig;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboard;
import ru.trick.springmangabot.makerKeyBord.KeyboardBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@PropertySource("classpath:hibernate.properties")
public class TG_bot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MainMenuServic mainMenuServic;
    private final KeyboardServic keyboardServic;
    private  final InlineKeyboard inlineKeyboard;
    private boolean helpStatus = false;

    @Autowired
    public TG_bot(BotConfig botConfig, MainMenuServic mainMenuServic,
                  KeyboardServic keyboardServic1, InlineKeyboard inlineKeyboard) {
        this.botConfig = botConfig;
        this.mainMenuServic = mainMenuServic;
        this.keyboardServic = keyboardServic1;
        this.inlineKeyboard = inlineKeyboard;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            final long chatId = callbackQuery.getMessage().getChatId();
            final long messageId = callbackQuery.getMessage().getMessageId();
            // default ответ, если получили неизвестную кнопку.
            BotApiMethod<?> callBackAnswer = keyboardServic.sendMessageMenu(chatId, "Вы вернулись в главное меню");


            switch (callbackQuery.getData()) {
                case "buttonEmperor" -> {
                    try {
                        execute(changeMessageButtonEmperor(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonStrongestEver" -> {
                    System.out.println("test2");
                }
                case "buttonMyFateIsAVillain" -> {
                    System.out.println("test3");
                }
                case "buttonDukePendragonRegression" -> {
                    System.out.println("test4");
                }
                case "buttonFullyArmed" -> {
                    System.out.println("test5");
                }
                case "buttonPlayGamesInPeace" -> {
                    System.out.println("test6");

                }
                case "buttonNextPage" -> {
                    try {
                        execute(editMessage(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonComeBack" -> {
                    try {
                        execute(editMessage2(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    try {
                        execute(callBackAnswer);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();                     // Текст пользователя
            String firstNameUser = update.getMessage().getChat().getFirstName();    // Имя пользователя
            long chatId = update.getMessage().getChatId();                          // ChatId пользователя
            String nickName = update.getMessage().getChat().getUserName();          // Для отображения @nickName и перехода по клику


            // Проверка условия на соответвие // Переедлать, временное решение
            Pattern pattern = Pattern.compile("[ОоТтВвЕе] [1-90]+ [А-Яа-яA-Za-z1-9_*:^#$!(+=0/]+");
            Matcher matcher = pattern.matcher(messageText);

            // Отправляем ответ из помощи // Попозже переделать
            if (chatId == 1088235267 && matcher.find()) mainMenuServic.returnMessageHelp(messageText);

            //     breakSwitch = false;   Нужно сделать админ доступ
            //   if (!helpStatus)         Сделать хелп доступ


            if (helpStatus) {
                switch (messageText) {
                    case "Главное меню" -> {
                        try {
                            execute(keyboardServic.sendMessageMenu(chatId, "Выход из режима помощи"));
                            helpStatus = false;
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове метода для возврата из меню помощи");
                            throw new RuntimeException(e);
                        }
                    }
                    default -> {
                        try {
                            execute(mainMenuServic.wrapperForDefaultReturnInHelo(chatId, nickName, firstNameUser, messageText));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при отправкисообщение в помощь ( default )");
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            if (!helpStatus) {
                switch (messageText) {
                    case "/start" -> {
                        try {
                            execute(mainMenuServic.complitStart(chatId, update));
                            execute(keyboardServic.sendMessageMenu(chatId, mainMenuServic.startMessage(firstNameUser)));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено при использовании /start.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Профиль" -> {
                        try {
                            execute(mainMenuServic.myProfile(chatId));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов для профиля.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Каталог" -> {
                        try {
//  ------------------------------    Можно добавить картинку / что-то еще....
                            execute(inlineMenu(chatId, "Что будем читать сегодня?"));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов для каталога.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Подписка" -> {
                        try {
                            execute(mainMenuServic.subscription(chatId));
                            execute(keyboardServic.sendMessageMenuSub(chatId, ""));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов подписки.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Главное меню" -> {
                        try {
                            execute(keyboardServic.sendMessageMenu(chatId, "Вы вернулись в главное меню"));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове метода для перехода в главное меню");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Закладки" -> {
                        try {
                            execute(mainMenuServic.sendMessage(chatId, "В процессе работы..."));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов закладки");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Пополнить баланс" -> {
                        try {
                            execute(mainMenuServic.sendMessage(chatId, "Временно не доступно."));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов пополнения баланса");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Купить подписку" -> {
                        try {
                            execute(mainMenuServic.buySub(chatId));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при покупки подписки");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Помощь" -> {
                        try {
                            execute(mainMenuServic.helpClick(chatId));
                            helpStatus = true;
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов помощи");
                            throw new RuntimeException(e);
                        }
                    }
                    default -> {
                        try {
                            execute(mainMenuServic.sendMessageForHelp(chatId, "Извините, такой команды нет."));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов default");
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }


//    }
//    private AnswerCallbackQuery makeAnswerCallbackQuery(String id, String word) {
//        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//        answerCallbackQuery.setCallbackQueryId(id);
//        answerCallbackQuery.setShowAlert(true);
//        answerCallbackQuery.setText(word);
//        return answerCallbackQuery;
//    }


    private DeleteMessage deleteMessage(CallbackQuery callbackQuery) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        deleteMessage.setChatId(callbackQuery.getMessage().getChatId());

        return deleteMessage;
    }

    private EditMessageReplyMarkup editMessage(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons2());

        return editMessage;
    }

    private EditMessageReplyMarkup editMessage2(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons());

        return editMessage;
    }

    private EditMessageReplyMarkup changeMessageButtonEmperor(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons());

        return editMessage;
    }


    public SendMessage inlineMenu(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons());
        return sendMessage;
    }







}



