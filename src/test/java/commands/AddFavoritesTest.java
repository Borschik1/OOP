package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class AddFavoritesTest {
    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Почта успешно добавлена в избранные");
    }

    @Test
    public void noInputData() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("add_favorites", new MessageInfo(0, "cringe amogus", user));
        Assert.assertEquals(writerMock.getText(), "Некорректные параметры команды, введите /help для получения справки");
    }

    @Test
    public void alreadyDone() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данный почтовый адрес уже был добавлен в избранное");
    }

}
