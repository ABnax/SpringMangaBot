package ru.trick.springmangabot.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.trick.springmangabot.config.BotConfig;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboard;
import ru.trick.springmangabot.model.PaymentRequest;
import ru.trick.springmangabot.model.Player;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@PropertySource("classpath:hibernate.properties")
public class TG_bot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MainMenuServic mainMenuServic;
    private final KeyboardServic keyboardServic;
    private final InlineKeyboard inlineKeyboard;
    private final PlayerService playerService;
    private final ChapterMangaService chapterMangaService;
    private final HttpClient httpClient;
    private final RestTemplate restTemplate;
    private boolean helpStatus = false;
    @Value("${qiwiApiKey}")
    private String qiwiApiKey ;
    @Value("${providerToken}")
    private String providerToken;

    @Autowired
    public TG_bot(BotConfig botConfig, MainMenuServic mainMenuServic,
                  KeyboardServic keyboardServic1, InlineKeyboard inlineKeyboard,
                  PlayerService playerService, ChapterMangaService chapterMangaService, HttpClient httpClient, RestTemplate restTemplate) {
        this.botConfig = botConfig;
        this.mainMenuServic = mainMenuServic;
        this.keyboardServic = keyboardServic1;
        this.inlineKeyboard = inlineKeyboard;
        this.playerService = playerService;
        this.chapterMangaService = chapterMangaService;
        this.httpClient = httpClient;
        this.restTemplate = restTemplate;
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
            mainMenuServic.checkSub(chatId);


            switch (callbackQuery.getData()) {
                case "buttonEmperor" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "Emperor"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonEmperorFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "EmperorFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonStrongestEver" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "StrongestEver"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonStrongestEverFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "StrongestEverFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonMyFateIsAVillain" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "MyFateIsAVillain"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonMyFateIsAVillainFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "MyFateIsAVillainFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonDukePendragonRegression" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "DukePendragonRegression"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonDukePendragonRegressionFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "DukePendragonRegressionFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonFullyArmed" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "FullyArmed"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonFullyArmedFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "FullyArmedFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonPlayGamesInPeace" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "PlayGamesInPeace"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonPlayGamesInPeaceFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "PlayGamesInPeaceFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonNextPage" -> {
                    try {
                        execute(keyboardServic.editMessage(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonComeBack" -> {
                    try {
                        execute(keyboardServic.editMessage2(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonNextPageFREE" -> {
                    try {
                        execute(keyboardServic.editMessageFREE(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "buttonComeBackFREE" -> {
                    try {
                        execute(keyboardServic.editMessage2FREE(callbackQuery));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "MySkillCopyingOthers" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "MySkillCopyingOthers"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "MySkillCopyingOthersFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "MySkillCopyingOthersFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "BlackSummoner" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonNameManga(callbackQuery, "BlackSummoner"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "BlackSummonerFREE" -> {
                    try {
                        execute(keyboardServic.changeMessageButtonFREE(callbackQuery, "BlackSummonerFREE"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "Подписка на Emperor" -> {
                    try {
                        execute(mainMenuServic.sendMessage(chatId, "Вы подписались на рассылку новых глав.\nКогда выйдет новая глава, Вам придет уведомление об этом."));
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
//                case "check_payment_status" -> {
//                    try {
//                        execute(checkPaymentStatus(chatId));
//
//                        execute(getPaymentStatusMessage(String.valueOf(chatId)));
//
//                    } catch (TelegramApiException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                case "pay" -> {
//
//                    try {
//                        execute(pay(1000L, chatId));
//
//                    } catch (TelegramApiException e) {
//                        throw new RuntimeException(e);
//                    }
//                }

                default -> {
                    try {
                        execute(callBackAnswer);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        if (update.hasPreCheckoutQuery()) {
            long chatId = update.getPreCheckoutQuery().getFrom().getId();
            PreCheckoutQuery preCheckoutQuery = update.getPreCheckoutQuery();
            handlePreCheckoutQuery(preCheckoutQuery, chatId);
        }


        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String firstNameUser = update.getMessage().getChat().getFirstName();
            long chatId = update.getMessage().getChatId();
            String nickName = update.getMessage().getChat().getUserName();



            Pattern pattern = Pattern.compile("[ОоТтВвЕе] [1-90]+ [А-Яа-яA-Za-z1-9_*:^#$!(+=0/]+");
            Matcher matcher = pattern.matcher(messageText);
            if (chatId == 1088235267 && matcher.find()) mainMenuServic.returnMessageHelp(messageText);




            if (helpStatus) {
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
                            execute(mainMenuServic.wrapperForDefaultReturnInHelo(chatId, nickName, firstNameUser, messageText));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при отправкисообщение в помощь ( default )");
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            if (!helpStatus) {
                mainMenuServic.checkSub(chatId);
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
                            execute(mainMenuServic.myProfile(chatId));
                        } catch (TelegramApiException e) {
                            log.error("Исключение было выброшено в при вызове методов для профиля.");
                            throw new RuntimeException(e);
                        }
                    }
                    case "Каталог" -> {
//  ------------------------------    Можно добавить картинку / что-то еще....
                        try {
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


                            //     Реализация через QIWI API -> TODO
                            //     execute(processPayment(1000L, String.valueOf(chatId)));
                            execute(sendInvoice(chatId));


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


    public SendInvoice sendInvoice(Long chatId) {
        SendInvoice sendInvoice = new SendInvoice();

        sendInvoice.setChatId(chatId);
        sendInvoice.setCurrency("RUB");
        sendInvoice.setProviderToken(providerToken);
        sendInvoice.setTitle("Пополнения кошелька");
        sendInvoice.setDescription(" ");
        sendInvoice.setPayload("text payload");
        sendInvoice.setPrices(Collections.singletonList(new LabeledPrice("Пополнить", 6000)));

        return sendInvoice;

    }

    private void handlePreCheckoutQuery(PreCheckoutQuery preCheckoutQuery, long chatId) {
        try {
            if (checkPayment(preCheckoutQuery.getInvoicePayload())) {
                sendApiMethod(new AnswerPreCheckoutQuery(preCheckoutQuery.getId(), true, "Pre-payment check succeeded"));
                Player tempUser = playerService.takeUser(chatId);
                tempUser.setBalance(tempUser.getBalance() + 60);
                playerService.update(tempUser);
            } else {
                sendApiMethod(new AnswerPreCheckoutQuery(preCheckoutQuery.getId(), false, "Pre-payment check failed"));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPayment(String invoicePayload) {
        // Придумать логику проверки.....
        return true;
    }

//    public void processPayment(Long amount, String userId) throws UnsupportedEncodingException {
//        HttpPost request = new HttpPost("https://api.qiwi.com/partner/bill/v1/bills/payment");
//        request.setHeader("Accept", "application/json");
//        request.setHeader("Content-Type", "application/json");
//        request.setHeader("Authorization", "Bearer " + qiwiApiKey);
//
//        // Set up the request payload with the payment information
//        String requestPayload = "{\"amount\":" + amount + ",\"currency\":\"RUB\",\"comment\":\"Payment for user " + userId + "\"}";
//        request.setEntity(new StringEntity(requestPayload));
//
//        try {
//            HttpResponse response = httpClient.execute(request);
//            int statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode == 200) {
//                // Payment was successful, update the user's account balance
//                System.out.println("200 - okay");
//                String responseBody = EntityUtils.toString(response.getEntity());
//
//            } else {
//
//            }
//        } catch (IOException e) {
//
//        }
//
//    }


    public SendMessage processPayment(Long amount, String userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText("Оплата будет произведена на QIWI кошелек.\nДля оплаты нажмите: <оплатить> " +
                "\nС Вас будет списано " + (amount / 100) + " рублей");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton("Проверить статус платежа"));
        firstRow.get(0).setCallbackData("check_payment_status");


        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(new InlineKeyboardButton("Оплатить"));
        secondRow.get(0).setCallbackData("pay");


        rows.add(secondRow);
        rows.add(firstRow);

        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        return sendMessage;
    }

    public SendMessage checkPaymentStatus(long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText("Checking payment status...");


        return sendMessage;
    }

    public SendMessage getPaymentStatusMessage(String paymentId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Long.valueOf(paymentId));
        try {
            String paymentStatus = getPaymentStatus(paymentId);
            if (paymentStatus.equals("SUCCESS")) {
                sendMessage.setText("Оплата прошла успешно.");
            } else {
                sendMessage.setText("Не удалось выполнить оплату.");
            }
        } catch (Exception e) {
            sendMessage.setText("Exception оплаты: \n" + e.getMessage());
        }
        return sendMessage;
    }

    public String getPaymentStatus(String paymentId) throws Exception {

        String QIWI_API_URL = "https://api.qiwi.com/api/v2/payment-history/payments";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(QIWI_API_URL + "/" + paymentId);
        request.addHeader("Authorization", "Bearer " + providerToken);

        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode != 200) {
            throw new Exception("Failed to get payment status, response code: " + responseCode);
        }

        String responseBody = EntityUtils.toString(response.getEntity());
        // Parse JSON response to get payment status
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        String paymentStatus = rootNode.get("status").textValue();

        return paymentStatus;
    }


    /*

    public PaymentResponse makePayment(Long amount, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(qiwiProperties.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentRequest request = new PaymentRequest(amount, userId);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentResponse> response = restTemplate.exchange(qiwiProperties.getUrl(),
                HttpMethod.POST, entity, PaymentResponse.class);

        return response.getBody();

     */


    public SendMessage pay(long amount, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText("Выполняется оплата на сумму - " + amount + " рублей");




        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(qiwiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentRequest request = new PaymentRequest(amount, String.valueOf(userId));
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Response> response = restTemplate.exchange("https://api.qiwi.com/sinap/api/v2/terms/99/payments",
                HttpMethod.POST, entity, Response.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Response paymentResponse = response.getBody();
            sendMessage.setText("Оплата выполнена успешно. Статус оплаты: " + paymentResponse.getStatus());
        } else {
            sendMessage.setText("Ошибка при выполнении оплаты");
        }


        return sendMessage;
    }


    public PaymentRequest makePayment(Long amount, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(qiwiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentRequest request = new PaymentRequest(amount, userId);

        HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentRequest> response = restTemplate.exchange("https://api.qiwi.com/sinap/api/v2/terms/99/payments",
                HttpMethod.POST, entity, PaymentRequest.class);

        return response.getBody();
    }


}


