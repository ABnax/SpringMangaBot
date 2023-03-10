package ru.trick.springmangabot.makerKeyBord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trick.springmangabot.model.Alert;
import ru.trick.springmangabot.model.ChapterManga;
import ru.trick.springmangabot.service.AlertService;
import ru.trick.springmangabot.service.ChapterMangaService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InlineKeyboard {

    private final ChapterMangaService chapterMangaService;
    private final AlertService alertService;

    @Autowired
    public InlineKeyboard(ChapterMangaService chapterMangaService, AlertService alertService) {
        this.chapterMangaService = chapterMangaService;
        this.alertService = alertService;
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

        buttonEmperor.setCallbackData("menu Emperor");
        buttonMyFateIsAVillain.setCallbackData("menu MyFateIsAVillain");
        buttonStrongestEver.setCallbackData("menu StrongestEver");
        buttonFullyArmed.setCallbackData("menu MySkillCopyingOthers");
        buttonPlayGamesInPeace.setCallbackData("menu BlackSummoner");
        buttonDukePendragonRegression.setCallbackData("menu DukePendragonRegression");
        buttonNextPage.setCallbackData("NextPage");


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


        buttonName1.setCallbackData("menu FullyArmed");
        buttonName2.setCallbackData("menu PlayGamesInPeace");
        buttonName3.setCallbackData("\uD83D\uDD1C");
        buttonName4.setCallbackData("\uD83D\uDD1C");
        buttonName5.setCallbackData("\uD83D\uDD1C");
        buttonName6.setCallbackData("\uD83D\uDD1C");
        buttonComeBack.setCallbackData("ComeBack");


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
        InlineKeyboardButton FREE = new InlineKeyboardButton("Все главы");
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
        buttonSub.setCallbackData("Alert " + name);
        buttonComeBack.setCallbackData("ComeBack");


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

        buttonEmperor.setCallbackData("menuFREE EmperorFREE");
        buttonMyFateIsAVillain.setCallbackData("menuFREE MyFateIsAVillainFREE");
        buttonStrongestEver.setCallbackData("menuFREE StrongestEverFREE");
        buttonFullyArmed.setCallbackData("menuFREE MySkillCopyingOthersFREE");
        buttonPlayGamesInPeace.setCallbackData("menuFREE BlackSummonerFREE");
        buttonDukePendragonRegression.setCallbackData("menuFREE DukePendragonRegressionFREE");
        buttonNextPage.setCallbackData("NextPage FREE");


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


        buttonName1.setCallbackData("menuFREE FullyArmedFREE");
        buttonName2.setCallbackData("menuFREE PlayGamesInPeaceFREE");
        buttonName3.setCallbackData("\uD83D\uDD1C");
        buttonName4.setCallbackData("\uD83D\uDD1C");
        buttonName5.setCallbackData("\uD83D\uDD1C");
        buttonName6.setCallbackData("\uD83D\uDD1C");
        buttonComeBack.setCallbackData("ComeBack FREE");


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

        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Все главы");

        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");
        buttonName1.setUrl(list.get(0).getUrl());

        buttonComeBack.setCallbackData("ComeBack FREE");

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


    public InlineKeyboardMarkup getInlineButtonAlert (long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<Alert> listAlert = alertService.takeAlertForUser(chatId);

        String nameManga = " - ";

        for (Alert ltAlert : listAlert) {

            switch (ltAlert.getNameManga()){
                case "Emperor" -> nameManga = "Как демон император стал дворецким";
                case "MyFateIsAVillain" -> nameManga = "Моя судьба злодея";
                case "StrongestEver" -> nameManga = "Сильнейший во все времена";
                case "MySkillCopyingOthers" -> nameManga = "Мой Навык Копирование Способностей Других";
                case "BlackSummoner" -> nameManga = "Черный Призыватель";
                case "DukePendragonRegression" -> nameManga = "Регрессия герцого Пендрагона";
                case "FullyArmed" -> nameManga = "Во всеоружии";
                case "PlayGamesInPeace" -> nameManga = "Я просто хочу спокойно играть в игры";
            }

            InlineKeyboardButton button  = new InlineKeyboardButton(nameManga);

            button.setCallbackData("BookMarks " + ltAlert.getNameManga());

            List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
            keyboardButtons.add(button);
            rowList.add(keyboardButtons);
        }


        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineButtonDeleteBookMarks(long chatId, String nameManga) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();



        InlineKeyboardButton buttonName1 = new InlineKeyboardButton(" ❌ Отписаться ❌ ");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton(" <--- Вернуться назад --- ");

        buttonName1.setCallbackData("BookMarksOFF " + nameManga);

        buttonComeBack.setCallbackData("ComeBackBookMarks");

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


    public InlineKeyboardMarkup getInlineButtonPay (String urlPay) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Оплатить");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton("Проверить оплату");

        buttonName1.setUrl(urlPay);

        buttonComeBack.setCallbackData("CheckPay");

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


    public InlineKeyboardMarkup getInlineButtonCancelPay () {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton("Отменить платеж");

        buttonComeBack.setCallbackData("CancelPay");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonComeBack);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup getInlineButtonIntermediatePay () {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton buttonName1 = new InlineKeyboardButton("Все верно");
        InlineKeyboardButton buttonComeBack = new InlineKeyboardButton("Отмена");

        buttonName1.setCallbackData("Pay");
        buttonComeBack.setCallbackData("Cancel");

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
