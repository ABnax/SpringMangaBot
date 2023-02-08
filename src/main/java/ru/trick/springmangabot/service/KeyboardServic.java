package ru.trick.springmangabot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboard;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;

@Component
public class KeyboardServic {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboard inlineKeyboard;
    private final AlertService alertService;

    @Autowired
    public KeyboardServic(ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboard inlineKeyboard, AlertService alertService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboard = inlineKeyboard;
        this.alertService = alertService;
    }

    public SendMessage sendMessageMenuSub(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub(chatId));
        return sendMessage;
    }

    public SendMessage sendMessageMenu(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }


    public DeleteMessage deleteMessage(Update update) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(update.getMessage().getMessageId());
        deleteMessage.setChatId(update.getMessage().getChatId());
        return deleteMessage;
    }

    public EditMessageReplyMarkup editMessage(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons2());

        return editMessage;
    }

    public EditMessageReplyMarkup editMessage2(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons());

        return editMessage;
    }

    public EditMessageReplyMarkup editMessageFREE(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons2FREE());

        return editMessage;
    }

    public EditMessageReplyMarkup editMessage2FREE(CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtonsFREE());

        return editMessage;
    }


    public EditMessageReplyMarkup changeMessageButtonNameManga(CallbackQuery callbackQuery, String name) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineButton(name));

        return editMessage;
    }


    public SendMessage inlineMenu(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtons());
        return sendMessage;
    }

    public SendMessage inlineMenuFREE(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineMessageButtonsFREE());
        return sendMessage;
    }

    public EditMessageReplyMarkup changeMessageButtonFREE(CallbackQuery callbackQuery, String name) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineButtonFREE(name));

        return editMessage;
    }

    public SendMessage sendMenuBookmarks(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        if (alertService.takeAlertForUser(chatId).size() == 0) {
            sendMessage.setText(textSend + "\nВы пока еще не добавили закладки.");
        } else {
            sendMessage.setReplyMarkup(inlineKeyboard.getInlineButtonAlert(chatId));
        }
        return sendMessage;
    }

    public EditMessageReplyMarkup changeMessageBookMarks(long chatId, CallbackQuery callbackQuery, String nemaManga) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineButtonDeleteBookMarks(chatId, nemaManga));
        return editMessage;
    }


    public EditMessageReplyMarkup editMessageBookMarks (CallbackQuery callbackQuery) {
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessage.setChatId(callbackQuery.getMessage().getChatId());
        editMessage.setReplyMarkup(inlineKeyboard.getInlineButtonAlert(callbackQuery.getMessage().getChatId()));

        return editMessage;
    }

    public SendMessage inlineMenuPay(long chatId, String textSend, String urlPay) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineButtonPay(urlPay));
        return sendMessage;
    }

    public SendMessage inlineMenuCancelPay(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineButtonCancelPay());
        return sendMessage;
    }
    public SendMessage inlineMenuIntermediatelPay(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineButtonIntermediatePay());
        return sendMessage;
    }



}
