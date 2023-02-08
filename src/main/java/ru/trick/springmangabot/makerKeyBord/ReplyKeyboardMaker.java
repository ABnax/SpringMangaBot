package ru.trick.springmangabot.makerKeyBord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.trick.springmangabot.ENUM.ButtonNameEnum;
import ru.trick.springmangabot.model.Player;
import ru.trick.springmangabot.service.PlayerService;
//import ru.betake.telegram.writeRead.constants.bot.ButtonNameEnum;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {


    private final PlayerService playerService;

    @Autowired
    public ReplyKeyboardMaker(PlayerService playerService) {
        this.playerService = playerService;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.GET_MY_PROFILE.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.GET_CATALOG.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.GET_SUBSCRIPTION.getButtonName()));
        row2.add(new KeyboardButton(ButtonNameEnum.GET_BOOKMARK.getButtonName()));
        row2.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboardForSub(Long chatId) {
        KeyboardRow row01 = new KeyboardRow();

        Player tempUser = playerService.takeUser(chatId);

        if (tempUser.isSubscription()) {
            row01.add(new KeyboardButton(ButtonNameEnum.BUY_SUB2.getButtonName()));
        } else {
            row01.add(new KeyboardButton(ButtonNameEnum.BUY_SUB.getButtonName()));
        }

        KeyboardRow row02 = new KeyboardRow();
        row02.add(new KeyboardButton(ButtonNameEnum.BACK_FOR_START_MENU.getButtonName()));


        List<KeyboardRow> keyboard2 = new ArrayList<>();
        keyboard2.add(row01);
        keyboard2.add(row02);

        final ReplyKeyboardMarkup replyKeyboardMarkupSub = new ReplyKeyboardMarkup();
        replyKeyboardMarkupSub.setKeyboard(keyboard2);
        replyKeyboardMarkupSub.setSelective(true);
        replyKeyboardMarkupSub.setResizeKeyboard(true);
        replyKeyboardMarkupSub.setOneTimeKeyboard(false);

        return replyKeyboardMarkupSub;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboardForProfiel(Long chatId) {

        Player tempUser = playerService.takeUser(chatId);
        KeyboardRow row002 = new KeyboardRow();

        if (tempUser.isSubscription()) {
            row002.add(new KeyboardButton(ButtonNameEnum.BUY_SUB2.getButtonName()));
        } else {
            row002.add(new KeyboardButton(ButtonNameEnum.BUY_SUB.getButtonName()));
        }

        row002.add(new KeyboardButton(ButtonNameEnum.BUY_MONEY.getButtonName()));

        KeyboardRow row22222 = new KeyboardRow();
        row22222.add(new KeyboardButton(ButtonNameEnum.BACK_FOR_START_MENU.getButtonName()));


        List<KeyboardRow> keyboard2 = new ArrayList<>();
        keyboard2.add(row002);
        keyboard2.add(row22222);

        final ReplyKeyboardMarkup replyKeyboardMarkupSub = new ReplyKeyboardMarkup();
        replyKeyboardMarkupSub.setKeyboard(keyboard2);
        replyKeyboardMarkupSub.setSelective(true);
        replyKeyboardMarkupSub.setResizeKeyboard(true);
        replyKeyboardMarkupSub.setOneTimeKeyboard(false);

        return replyKeyboardMarkupSub;
    }


    public ReplyKeyboardMarkup getMainMenu() {

        KeyboardRow row012 = new KeyboardRow();
        row012.add(new KeyboardButton(ButtonNameEnum.BACK_FOR_START_MENU.getButtonName()));

        List<KeyboardRow> keyboard2 = new ArrayList<>();
        keyboard2.add(row012);

        final ReplyKeyboardMarkup replyKeyboardMarkupSub = new ReplyKeyboardMarkup();
        replyKeyboardMarkupSub.setKeyboard(keyboard2);
        replyKeyboardMarkupSub.setSelective(true);
        replyKeyboardMarkupSub.setResizeKeyboard(true);
        replyKeyboardMarkupSub.setOneTimeKeyboard(false);

        return replyKeyboardMarkupSub;
    }
}