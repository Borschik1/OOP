package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class StartNotificationTest {

    @Test
    public void noFindAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        User user = new User((long) 0);
        bot.process("start_notification", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }

    @Test
    public void noLogin() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        User user = new User((long) 0);
        bot.process("start_notification", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Выберете необходимый почтовый ящик");
    }

    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        bot.process("start_notification", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Уведомления для данного ящика успешно включены");
    }

    @Test
    public void favoritesListEmpty() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("start_notification", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Выберете необходимый почтовый ящик");
    }
}
