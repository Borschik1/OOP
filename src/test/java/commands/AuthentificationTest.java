package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class AuthentificationTest {

    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        Assert.assertEquals(writerMock.getText(), "Аутентификация успешно пройдена");
    }

    @Test
    public void incorrectArgs() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Некорректные параметры команды, введите /help для получения справки");
    }

    @Test
    public void incorrectLoginData() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com sеееeghsactjibqzdo", user));
        Assert.assertEquals(writerMock.getText(), "Неверный логин или пароль");
    }

    @Test
    public void alreadyLogin() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        Assert.assertEquals(writerMock.getText(), "Данный почтовый адрес уже был аутентифицирован. Для изменения пароля удалите эту почту и аутентифицируйте её заново");
    }
}
