package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class EndNotificationTest {

    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new EndNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        bot.process("start_notification", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        bot.process("end_notification", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Уведомления для данной почты успешно удалены");
    }

    @Test
    public void alreadyOff() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new EndNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("end_notification", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Уведомления для данного ящика уже выключнены");
    }

    @Test
    public void noStartNotification() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new EndNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("end_notification", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Ни для одного ящика уведомления не включены");
    }

    @Test
    public void incorrectAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new StartNotification());
        bot.addCommand(new EndNotification());
        bot.addCommand(new AddFavorites());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        bot.process("start_notification", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        bot.process("end_notification", new MessageInfo(0, "amogus@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }
}
