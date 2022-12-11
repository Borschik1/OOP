package enums;

public enum MessagesTemplates {

    START_MESSAGE("Я родился! Готов помочь тебе работать с почтой без использования мобильного приложения. Чтобы узнать о моем функционале, напиши /help"),
    AUTH_COMPLETE("Аутентификация успешно пройдена"),
    ADD_FAVORITES_COMPLETE("Почта успешно добавлена в избранные"),
    EMPTY_MAIL("Почта пуста"),
    DELETE_COMPLETE("Почта успешно удалена"),
    DELETE_FAVORITES_COMPLETE("Почта успешно удалена из избранных"),
    DELETE_NOTIFICATION_COMPLETE("Уведомления для данной почты успешно удалены"),
    AUTH_LOGIN_ALREADY_SEEN("Данный почтовый адрес уже был аутентифицирован. Для изменения пароля удалите эту почту и аутентифицируйте её заново"),
    FAVORITES_LOGIN_ALREADY_SEEN("Данный почтовый адрес уже был добавлен в избранное"),
    MAIL_NOT_FOUND("Данная почта не найдена. Перепроверьте введенный логин"),
    MAIL_WRONG_LOGIN_PASSWORD("Неверный логин или пароль"),
    INCORRECT_ARGS("Некорректные параметры команды, введите /help для получения справки"),
    READ_CHOOSE_MAIL("Выберете необходимый почтовый ящик"),
    READ_CHOOSE_LETTERS_NUMBER("Выберете количество писем, которое необходимо считать"),
    MAILBOX_NOTIFICATION_ALREADY_START("Уведомления для данного ящика уже включены"),
    MAILBOX_NOTIFICATION_ALREADY_END("Уведомления для данного ящика уже выключнены"),
    MAILBOX_NOTIFICATION_EMPTY("Ни для одного ящика уведомления не включены"),
    MAILBOX_NOTIFICATION_COMPLETE("Уведомления для данного ящика успешно включены"),
    USER_MAIL_LIST_EMPTY("Невозможно получить список писем неавторизованного пользователя. Воспользуйтесь командой /new_mail, чтобы залогиниться"),
    USER_FAVORITY_LIST_EMPTY("Список избраных адресов пуст");

    public final String text;

    MessagesTemplates(String text) {
        this.text = text;
    }
}