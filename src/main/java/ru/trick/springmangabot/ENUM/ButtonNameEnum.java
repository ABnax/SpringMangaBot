package ru.trick.springmangabot.ENUM;

public enum ButtonNameEnum {
//    GET_MY_PROFILE("\uD83D\uDEB9 Профиль"),
//    GET_CATALOG("\uD83D\uDCD6 Каталог"),
//    GET_SUBSCRIPTION("\uD83D\uDCB0 Подписка"),
//    GET_BOOKMARK("\uD83D\uDCDA Закладки"),
   // HELP_BUTTON("\uD83D\uDEC2 Помощь"),
//    BACK_FOR_START_MENU ("↪️ Главное меню"),
    HELP_BUTTON("Помощь"),
    GET_MY_PROFILE("Профиль"),
    GET_CATALOG("Каталог"),
    GET_SUBSCRIPTION("Подписка"),
    GET_BOOKMARK("Закладки"),

    BACK_FOR_START_MENU ("Главное меню"),
    BACK_FOR_START_MENU2 ("Назад"),
    BUY_MONEY ("Пополнить баланс"),
    BUY_SUB("Купить подписку");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
