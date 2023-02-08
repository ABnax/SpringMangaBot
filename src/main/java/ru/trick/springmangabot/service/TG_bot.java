package ru.trick.springmangabot.service;


import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.trick.springmangabot.config.BotConfig;
import ru.trick.springmangabot.model.PayToken;
import ru.trick.springmangabot.model.Player;


import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
@PropertySource("classpath:hibernate.properties")
public class TG_bot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MainMenuServic mainMenuServic;
    private final KeyboardServic keyboardServic;
    private final PlayerService playerService;
    private final AlertService alertService;
    private final PayTokenService payTokenService;
    private boolean helpStatus = false;
    private boolean payStatus = false;
    private boolean startPay = false;
    private int moneyPayNow;

    public int getMoneyPayNow() {
        return moneyPayNow;
    }

    public void setMoneyPayNow(int moneyPayNow) {
        this.moneyPayNow = moneyPayNow;
    }

    @Value("${chatIdAdmin}")
    private long chatIdAdmin;

    @Value("${publicKey}")
    private String publicKey;

    @Value("${secretKey}")
    private String secretKey;


    @Autowired
    public TG_bot(BotConfig botConfig, MainMenuServic mainMenuServic, KeyboardServic keyboardServic1,
                  PlayerService playerService, AlertService alertService, PayTokenService payTokenService) {
        this.botConfig = botConfig;
        this.mainMenuServic = mainMenuServic;
        this.keyboardServic = keyboardServic1;
        this.playerService = playerService;
        this.alertService = alertService;
        this.payTokenService = payTokenService;
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
            mainMenuServic.checkSub(chatId);
            Player tempUser = playerService.takeUser(chatId);

            String[] word = callbackQuery.getData().split("\\s");
            switch (word[0]) {
                case "menu" -> {
                    try {
                        if (tempUser.isSubscription()) {
                            execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, word[1]));
                        }

                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "menuFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, word[1]));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "NextPage" -> {
                    try {
                        if (word.length == 1) {
                            execute(keyboardServic.editMessage(callbackQuery));
                        } else {
                            execute(keyboardServic.editMessageFREE(callbackQuery));
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "ComeBack" -> {
                    try {
                        if (word.length == 1) {
                            execute(keyboardServic.editMessage2(callbackQuery));
                        } else {
                            execute(keyboardServic.editMessage2FREE(callbackQuery));
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "Alert" -> {
                    try {
                        if (!alertService.checkAlert(chatId, word[1])) {
                            alertService.addAlert(chatId, word[1]);
                            execute(mainMenuServic.sendMessage(chatId, "Вы подписались на рассылку новых глав.\nКогда выйдет новая глава, Вам придет уведомление об этом."));
                        } else {
                            execute(mainMenuServic.sendMessage(chatId, "Вы уже подписаны на рассылку."));
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "BookMarks" -> {
                    try {
                        execute(keyboardServic.changeMessageBookMarks(chatId, callbackQuery, word[1]));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "ComeBackBookMarks" -> {
                    try {
                        execute(keyboardServic.editMessageBookMarks(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "BookMarksOFF" -> {
                    try {
                        mainMenuServic.BookMarksOFF(chatId, word[1]);
                        execute(mainMenuServic.sendMessage(chatId, "Вы отписались."));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "\uD83D\uDD1C" -> {
                    try {
                        execute(mainMenuServic.sendMessage(chatId, "В данный момент недоступно."));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "CheckPay" -> {
                    try {
                        String result = checkStatus(chatId);


                        if (result.equals("BillStatus{value='WAITING'}")) {
                            execute(mainMenuServic.sendMessage(chatId, "Платеж не оплачен."));
                        } else if (result.equals("BillStatus{value='PAID'}")) {
                            execute(mainMenuServic.sendMessage(chatId, "Платеж выполнен!"));
                            Player tempPlayer = playerService.takeUser(chatId);
                            int money = payTokenService.takeToken(chatId).getMoney();
                            tempPlayer.setBalance(tempPlayer.getBalance() + money);
                            playerService.update(tempPlayer);
                            payTokenService.deleteToken(chatId);
                            //  keyboardServic.deleteMessage(update);
                        } else {
                            execute(mainMenuServic.sendMessage(chatId, "Что-то пошло не так ... "));
                        }

                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "CancelPay" -> {
                    try {
                        String token = payTokenService.takeToken(chatId).toString();
                        payTokenService.deleteToken(chatId);
                        execute(mainMenuServic.sendMessage(chatId, "Оплата была отменена."));
                        cancelBill(token);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    try {
                        execute(keyboardServic.sendMessageMenu(chatId, "Что-то пошла не так. \nВы вернулись в главное меню"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String firstNameUser = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();
            String nickName = update.getMessage().getChat().getUserName();

            boolean tempAdminHelpStatus = true;

            String[] words = messageText.split("\\s");

            // ADMIN команды
            if (chatId == chatIdAdmin) {
                switch (words[0]) {
                    case "Ответ" -> {
                        try {
                            execute(mainMenuServic.returnMessageHelp(messageText));
                            tempAdminHelpStatus = false;
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Бан" -> {
                        try {
                            execute(mainMenuServic.banForHelp(messageText));
                            tempAdminHelpStatus = false;
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Разблокировать" -> {
                        try {
                            execute(mainMenuServic.unBanForHelp(messageText));
                            tempAdminHelpStatus = false;
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Добавить" -> {
                        String nameManga = "-";
                        switch (words[1]) {
                            case "Emperor" -> nameManga = "Как демон император стал дворецким";
                            case "MyFateIsAVillain" -> nameManga = "Моя судьба злодея";
                            case "StrongestEver" -> nameManga = "Сильнейший во все времена";
                            case "MySkillCopyingOthers" -> nameManga = "Мой Навык Копирование Способностей Других";
                            case "BlackSummoner" -> nameManga = "Черный Призыватель";
                            case "DukePendragonRegression" -> nameManga = "Регрессия герцого Пендрагона";
                            case "FullyArmed" -> nameManga = "Во всеоружии";
                            case "PlayGamesInPeace" -> nameManga = "Я просто хочу спокойно играть в игры";
                        }

                        try {
                            execute(mainMenuServic.mangaAdd(messageText, update));
                            List<Long> listForChatId = mainMenuServic.sendMessageForUpdate(words[1]);
                            for (Long lt : listForChatId) {
                                execute(mainMenuServic.sendMessage(lt, "Вышла новая глава! \n" + nameManga + "\nГлава № " + words[2]));
                            }
                            tempAdminHelpStatus = false;
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "Список" -> {
                        try {
                            execute(mainMenuServic.getListForName(messageText, chatId));
                            tempAdminHelpStatus = false;
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }


            if (helpStatus && tempAdminHelpStatus && !payStatus) {
                mainMenuServic.checkSub(chatId);
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
                            if (!playerService.takeUser(chatId).isBanStatus()) {
                                execute(mainMenuServic.wrapperForDefaultReturnInHelo(chatId, nickName, firstNameUser, messageText, chatIdAdmin));
                            } else {
                                execute(mainMenuServic.sendMessage(chatId, "Вам была выдана блокировка за флуд в <Помощь>" +
                                        "\nДля разблокировки обращайтесь к -  @ballefant "));
                            }
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при отправкисообщение в помощь ( default )");
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            if (payStatus) {
                try {
                    try {
                        int userInput = Integer.parseInt(messageText);
                        execute(mainMenuServic.sendMessage(chatId, "Вы ввели сумму: " + userInput + "  руб.\n"));
                        payStatus = false;
                        startPay = true;
                        setMoneyPayNow(userInput);
                    } catch (NumberFormatException e) {
                        execute(mainMenuServic.sendMessage(chatId, "Вы ввели неправильную сумму. \n" +
                                "Для того, чтобы попробовать еще раз, нажмите кнопку пополнить баланс еще раз."));
                        payStatus = false;
                        return;
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }


            if (!helpStatus && tempAdminHelpStatus && !payStatus) {
                if (!messageText.equals("/start")) mainMenuServic.checkSub(chatId);
                switch (messageText) {
                    case "/start" -> {
                        try {
                            execute(mainMenuServic.complitStart(chatId, update));
                            execute(keyboardServic.sendMessageMenu(chatId, ""));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено при использовании /start.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Профиль" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
                            execute(mainMenuServic.myProfile(chatId));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов для профиля.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Каталог" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
                            Player tempUser = playerService.takeUser(chatId);
                            if (tempUser.isSubscription()) {
                                execute(keyboardServic.inlineMenu(chatId, "Что будем читать сегодня?"));
                            } else {
                                execute(mainMenuServic.sendMessage(chatId, "Без активированной подписки Вам доступны только бесплатные главы."));
                                execute(keyboardServic.inlineMenuFREE(chatId, "Выберите произведение: "));
                            }
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов для каталога.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Подписка" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
                            execute(mainMenuServic.subscription(chatId));
                            execute(keyboardServic.sendMessageMenuSub(chatId, ""));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов подписки.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Главное меню" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
                            execute(keyboardServic.sendMessageMenu(chatId, "Вы вернулись в главное меню"));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове метода для перехода в главное меню");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Закладки" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
                            execute(keyboardServic.sendMenuBookmarks(chatId, "Ваш список подписок: "));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов закладки");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Пополнить баланс" -> {
                        try {
                            execute(mainMenuServic.sendMessage(chatId, "Введите сумму: "));
                            payStatus = true;

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
                    case "Продлить подписку" -> {
                        try {
                            execute(keyboardServic.deleteMessage(update));
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
                    case "Лист" -> {
                        try {
                            if (chatId == chatIdAdmin) {
                                execute(mainMenuServic.sendMessage(chatId,
                                        """
                                                Название манг:\s
                                                MyFateIsAVillain - Моя судьба злодея
                                                DukePendragonRegression - Регрессия герцого Пендрагона
                                                StrongestEver - Сильнейший во все времена
                                                Emperor - Как демон император стал дворецким
                                                FullyArmed - Во всеоружии
                                                PlayGamesInPeace - Я просто хочу спокойно играть в игры
                                                MySkillCopyingOthers - Мой Навык Копирование Способностей Других
                                                BlackSummoner - Черный Призыватель"""));
                            } else {
                                execute(mainMenuServic.sendMessageForHelp(chatId, "Извините, такой команды нет."));
                            }
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов default");
                            throw new RuntimeException(e);
                        }
                    }
                    default -> {
                        try {
                            if (startPay) {
                                startPay = false;
                                if (payTokenService.takeTokenCheck(chatId) != null) {
                                    execute(keyboardServic.inlineMenuCancelPay(chatId, """
                                            Вы не завершили прошлый платеж.
                                            Чтобы продолжить Вам нужно отменить старый запрос, нажав на кнопку.
                                            И затем заново нажать кнопку пополнить баланс."""));
                                } else {
                                    startPay = false;
                                    BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);
                                    Random random = new Random();
                                    String payToken = String.valueOf(chatId) + "_" + String.valueOf(random.nextInt(100000));
                                    System.out.println(payToken);
                                    int money = getMoneyPayNow();
                                    setMoneyPayNow(0);
                                    payTokenService.addToken(chatId, payToken, money);

                                    MoneyAmount amount = new MoneyAmount(
                                            BigDecimal.valueOf(Double.valueOf(money)),
                                            Currency.getInstance("RUB")
                                    );

                                    String url = client.createPaymentForm(new PaymentInfo(publicKey, amount, payToken, null));

                                    execute(keyboardServic.inlineMenuPay(chatId, "Пополнение счета в Telegram боте - MangaBot", url));
                                }
                            } else {

                                execute(mainMenuServic.sendMessageForHelp(chatId, "Извините, такой команды нет."));
                            }
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов default");
                            throw new RuntimeException(e);
                        }
                    }
                }
            }


        }
    }

    public String checkStatus(Long chatId) {
        BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);
        PayToken tempToken = payTokenService.takeToken(chatId);
        String tokenCheck = tempToken.getPayToken();
        BillResponse response = client.getBillInfo(tokenCheck);
        return response.getStatus().getValue().toString();
    }


    public void cancelBill(String PayToken) {
        BillPaymentClient client1 = BillPaymentClientFactory.createDefault(secretKey);
        client1.cancelBill(PayToken);
    }




}


