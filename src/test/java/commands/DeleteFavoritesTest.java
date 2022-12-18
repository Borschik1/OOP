package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class DeleteFavoritesTest {

    @Test
    public void noAddFavorites() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteFavorites());
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("delete_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }

    @Test
    public void noFindAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteFavorites());
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("delete_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }

    @Test
    public void noAddAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteFavorites());
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("delete_favorites", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Список избраных адресов пуст");
    }

    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteFavorites());
        bot.addCommand(new AddFavorites());
        User user = new User((long) 0);
        bot.process("add_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        bot.process("delete_favorites", new MessageInfo(0, "leorozen1415@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Почта успешно удалена из избранных");
    }
}
