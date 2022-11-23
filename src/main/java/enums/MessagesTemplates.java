package enums;

public enum MessagesTemplates {

    AUTH_LOGIN_ALREADY_SEEN("Данный почтовый адрес уже был аутентифицирован. Для изменения пароля удалите эту почту и аутентифицируйте её заново"),
    MAIL_NOT_FOUND("Данная почта не найдена. Перепроверьте введенный логин."),
    MAIL_WRONG_LOGIN_PASSWORD("Неверный логин или пароль"),
    INCORRECT_ARGS("Некорректные параметры команды, введите /help для получения справки");

    public final String text;

    MessagesTemplates(String text) {
        this.text = text;
    }
}