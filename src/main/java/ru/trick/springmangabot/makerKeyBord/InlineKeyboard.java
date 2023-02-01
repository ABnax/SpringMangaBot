package ru.trick.springmangabot.makerKeyBord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trick.springmangabot.model.ChapterManga;
import ru.trick.springmangabot.service.ChapterMangaService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        InlineKeyboardButton buttonMyFateIsAVillain = new InlineKeyboardButton("Моя судьба злодея");
        InlineKeyboardButton buttonStrongestEver = new InlineKeyboardButton("Сильнейший во все времена");
        InlineKeyboardButton buttonFullyArmed = new InlineKeyboardButton("Мой Навык Копирование Способностей Других");
        InlineKeyboardButton buttonPlayGamesInPeace = new InlineKeyboardButton("Черный Призыватель");
        InlineKeyboardButton buttonDukePendragonRegression = new InlineKeyboardButton("Регрессия герцого Пендрагона");
        InlineKeyboardButton buttonNextPage = new InlineKeyboardButton(" --- Следующая страница ---> ");

        buttonEmperor.setCallbackData("buttonEmperor");
        buttonMyFateIsAVillain.setCallbackData("buttonMyFateIsAVillain");
        buttonStrongestEver.setCallbackData("buttonStrongestEver");
        buttonFullyArmed.setCallbackData("MySkillCopyingOthers");
        buttonPlayGamesInPeace.setCallbackData("BlackSummoner");
        buttonDukePendragonRegression.setCallbackData("buttonDukePendragonRegression");
        buttonNextPage.setCallbackData("buttonNextPage");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonEmperor);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonMyFateIsAVillain);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonStrongestEver);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonFullyArmed);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();

        keyboardButtonsRow5.add(buttonPlayGamesInPeace);
        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(buttonDukePendragonRegression);

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


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Во всеоружии");
        InlineKeyboardButton buttonName2 = new InlineKeyboardButton("Я просто хочу спокойно играть в игры");
        InlineKeyboardButton buttonName3 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName4 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName5 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName6 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");


        buttonName1.setCallbackData("buttonFullyArmed");
        buttonName2.setCallbackData("buttonPlayGamesInPeace");
        buttonName3.setCallbackData("\uD83D\uDD1C");
        buttonName4.setCallbackData("\uD83D\uDD1C");
        buttonName5.setCallbackData("\uD83D\uDD1C");
        buttonName6.setCallbackData("\uD83D\uDD1C");
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


    public InlineKeyboardMarkup getInlineButton(String name) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<ChapterManga> list = chapterMangaService.findAll(name);
        List<ChapterManga> free = chapterMangaService.findAll(name + "FREE");
        list = list.stream().sorted(Comparator.comparingInt(ChapterManga::getNumber).reversed()).collect(Collectors.toList());


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton(String.valueOf(list.get(0).getNumber()));
        InlineKeyboardButton buttonName2 = new InlineKeyboardButton(String.valueOf(list.get(1).getNumber()));
        InlineKeyboardButton buttonName3 = new InlineKeyboardButton(String.valueOf(list.get(2).getNumber()));
        InlineKeyboardButton buttonName4 = new InlineKeyboardButton(String.valueOf(list.get(3).getNumber()));
        InlineKeyboardButton buttonName5 = new InlineKeyboardButton(String.valueOf(list.get(4).getNumber()));
        InlineKeyboardButton buttonName6 = new InlineKeyboardButton(String.valueOf(list.get(5).getNumber()));
        InlineKeyboardButton buttonName7 = new InlineKeyboardButton(String.valueOf(list.get(6).getNumber()));
        InlineKeyboardButton buttonName8 = new InlineKeyboardButton(String.valueOf(list.get(7).getNumber()));
        InlineKeyboardButton buttonName9 = new InlineKeyboardButton(String.valueOf(list.get(8).getNumber()));
        InlineKeyboardButton buttonName10 = new InlineKeyboardButton(String.valueOf(list.get(9).getNumber()));
        InlineKeyboardButton FREE = new InlineKeyboardButton("Остальные главы");
        InlineKeyboardButton buttonSub = new InlineKeyboardButton("Подписаться на выход новых глав");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");


        buttonName1.setUrl(list.get(0).getUrl());
        buttonName2.setUrl(list.get(1).getUrl());
        buttonName3.setUrl(list.get(2).getUrl());
        buttonName4.setUrl(list.get(3).getUrl());
        buttonName5.setUrl(list.get(4).getUrl());
        buttonName6.setUrl(list.get(5).getUrl());
        buttonName7.setUrl(list.get(6).getUrl());
        buttonName8.setUrl(list.get(7).getUrl());
        buttonName9.setUrl(list.get(8).getUrl());
        buttonName10.setUrl(list.get(9).getUrl());
        FREE.setUrl(free.get(0).getUrl());
        buttonSub.setCallbackData("Подписка на " + list.get(0).getNameManga());
        buttonComeBack.setCallbackData("buttonComeBack");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonName1);
        keyboardButtonsRow1.add(buttonName2);
        keyboardButtonsRow1.add(buttonName3);
        keyboardButtonsRow1.add(buttonName4);
        keyboardButtonsRow1.add(buttonName5);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonName6);
        keyboardButtonsRow2.add(buttonName7);
        keyboardButtonsRow2.add(buttonName8);
        keyboardButtonsRow2.add(buttonName9);
        keyboardButtonsRow2.add(buttonName10);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(FREE);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonSub);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(buttonComeBack);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageButtonsFREE() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonEmperor = new InlineKeyboardButton("Как демон император стал дворецким");
        InlineKeyboardButton buttonMyFateIsAVillain = new InlineKeyboardButton("Моя судьба злодея");
        InlineKeyboardButton buttonStrongestEver = new InlineKeyboardButton("Сильнейший во все времена");
        InlineKeyboardButton buttonFullyArmed = new InlineKeyboardButton("Мой Навык Копирование Способностей Других");
        InlineKeyboardButton buttonPlayGamesInPeace = new InlineKeyboardButton("Черный Призыватель");
        InlineKeyboardButton buttonDukePendragonRegression = new InlineKeyboardButton("Регрессия герцого Пендрагона");
        InlineKeyboardButton buttonNextPage = new InlineKeyboardButton(" --- Следующая страница ---> ");

        buttonEmperor.setCallbackData("buttonEmperorFREE");
        buttonMyFateIsAVillain.setCallbackData("buttonMyFateIsAVillainFREE");
        buttonStrongestEver.setCallbackData("buttonStrongestEverFREE");
        buttonFullyArmed.setCallbackData("MySkillCopyingOthersFREE");
        buttonPlayGamesInPeace.setCallbackData("BlackSummonerFREE");
        buttonDukePendragonRegression.setCallbackData("buttonDukePendragonRegressionFREE");
        buttonNextPage.setCallbackData("buttonNextPageFREE");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonEmperor);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonMyFateIsAVillain);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(buttonStrongestEver);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(buttonFullyArmed);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();

        keyboardButtonsRow5.add(buttonPlayGamesInPeace);
        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(buttonDukePendragonRegression);

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

    public InlineKeyboardMarkup getInlineMessageButtons2FREE() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Во всеоружии");
        InlineKeyboardButton buttonName2 = new InlineKeyboardButton("Я просто хочу спокойно играть в игры");
        InlineKeyboardButton buttonName3 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName4 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName5 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonName6 = new InlineKeyboardButton("\uD83D\uDD1C");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");


        buttonName1.setCallbackData("buttonFullyArmedFREE");
        buttonName2.setCallbackData("buttonPlayGamesInPeaceFREE");
        buttonName3.setCallbackData("\uD83D\uDD1C");
        buttonName4.setCallbackData("\uD83D\uDD1C");
        buttonName5.setCallbackData("\uD83D\uDD1C");
        buttonName6.setCallbackData("\uD83D\uDD1C");
        buttonComeBack.setCallbackData("buttonComeBackFREE");


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


    public InlineKeyboardMarkup getInlineButtonFREE(String name) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<ChapterManga> list = chapterMangaService.findAll(name);

        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Бесплатные главы");

        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");
        buttonName1.setUrl(list.get(0).getUrl());

        buttonComeBack.setCallbackData("buttonComeBackFREE");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonName1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonComeBack);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


}
