package ru.trick.springmangabot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.trick.springmangabot.makerKeyBord.InlineKeyboard;
import ru.trick.springmangabot.makerKeyBord.ReplyKeyboardMaker;

@Component
public class KeyboardServic {

    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboard inlineKeyboard;

    @Autowired
    public KeyboardServic(ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboard inlineKeyboard) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboard = inlineKeyboard;
    }

    public SendMessage sendMessageMenuSub(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboardForSub());
        return sendMessage;
    }

    public SendMessage sendMessageMenu(long chatId, String textSend) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), textSend);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }


    public DeleteMessage deleteMessage(CallbackQuery callbackQuery) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        deleteMessage.setChatId(callbackQuery.getMessage().getChatId());
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


}
