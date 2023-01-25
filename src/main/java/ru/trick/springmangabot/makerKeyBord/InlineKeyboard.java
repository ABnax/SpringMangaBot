package ru.trick.springmangabot.makerKeyBord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trick.springmangabot.service.ChapterMangaService;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboard {

    private final ChapterMangaService chapterMangaService;

    @Autowired
    public InlineKeyboard(ChapterMangaService chapterMangaService) {
        this.chapterMangaService = chapterMangaService;
    }

    public InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonEmperor = new InlineKeyboardButton("Как демон император стал дворецким");
        InlineKeyboardButton buttonStrongestEver = new InlineKeyboardButton("Сильнейший во все времена");
        InlineKeyboardButton buttonMyFateIsAVillain = new InlineKeyboardButton("Моя судьба злодея");
        InlineKeyboardButton buttonDukePendragonRegression = new InlineKeyboardButton("Регрессия герцого Пендрагона");
        InlineKeyboardButton buttonFullyArmed = new InlineKeyboardButton("Во всеоружии");
        InlineKeyboardButton buttonPlayGamesInPeace = new InlineKeyboardButton("Я просто хочу спокойно играть в игры");
        InlineKeyboardButton buttonNextPage = new InlineKeyboardButton(" --- Следующая страница ---> ");

        buttonEmperor.setCallbackData("buttonEmperor");
        buttonStrongestEver.setCallbackData("buttonStrongestEver");
        buttonMyFateIsAVillain.setCallbackData("buttonMyFateIsAVillain");
        buttonDukePendragonRegression.setCallbackData("buttonDukePendragonRegression");
        buttonFullyArmed.setCallbackData("buttonFullyArmed");
        buttonPlayGamesInPeace.setCallbackData("buttonPlayGamesInPeace");
        buttonNextPage.setCallbackData("buttonNextPage");



        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonEmperor);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonStrongestEver);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonMyFateIsAVillain);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonDukePendragonRegression);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(buttonFullyArmed);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(buttonPlayGamesInPeace);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        keyboardButtonsRow7.add(buttonNextPage);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup getInlineMessageButtons2() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("name1");
        InlineKeyboardButton buttonName2 = new InlineKeyboardButton("name2");
        InlineKeyboardButton buttonName3 = new InlineKeyboardButton("name3");
        InlineKeyboardButton buttonName4 = new InlineKeyboardButton("name4");
        InlineKeyboardButton buttonName5 = new InlineKeyboardButton("name5");
        InlineKeyboardButton buttonName6 = new InlineKeyboardButton("name6");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");


        buttonName1.setCallbackData("buttonName1");
        buttonName2.setCallbackData("buttonName2");
        buttonName3.setCallbackData("buttonName3");
        buttonName4.setCallbackData("buttonName4");
        buttonName5.setCallbackData("buttonName5");
        buttonName6.setCallbackData("buttonName6");
        buttonComeBack.setCallbackData("buttonComeBack");




        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonName1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonName2);
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonName3);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonName4);
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(buttonName5);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(buttonName6);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        keyboardButtonsRow7.add(buttonComeBack);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup getInlineButtonEmperor() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();




        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("name1");
        InlineKeyboardButton buttonName2 = new InlineKeyboardButton("name2");
        InlineKeyboardButton buttonName3 = new InlineKeyboardButton("name3");
        InlineKeyboardButton buttonName4 = new InlineKeyboardButton("name4");
        InlineKeyboardButton buttonName5 = new InlineKeyboardButton("name5");
        InlineKeyboardButton buttonName6 = new InlineKeyboardButton("name6");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");


        buttonName1.setCallbackData("buttonName1");
        buttonName2.setCallbackData("buttonName2");
        buttonName3.setCallbackData("buttonName3");
        buttonName4.setCallbackData("buttonName4");
        buttonName5.setCallbackData("buttonName5");
        buttonName6.setCallbackData("buttonName6");
        buttonComeBack.setCallbackData("buttonComeBack");




        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonName1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonName2);
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonName3);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonName4);
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(buttonName5);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(buttonName6);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        keyboardButtonsRow7.add(buttonComeBack);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
