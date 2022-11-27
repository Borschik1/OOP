package commands;

import com.pengrad.telegrambot.TelegramBot;
import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import struct.MessageInfo;

public class EchoTest {

    @Test
    public void test1() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("echo", new MessageInfo(0, "hello", user));
        Assert.assertEquals(writerMock.getText(), "hello");
    }
    @Test
    public void test2() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("echo", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Введите сообщение после команды");
    }
    @Test
    public void test3() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("wjgnwkg", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Такой команды не найдено");
    }
}
