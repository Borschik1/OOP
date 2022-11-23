package commands;

import com.pengrad.telegrambot.TelegramBot;
import domain.User;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import struct.MessageInfo;

public class HelpTest {

    @Test
    public void test1(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "echo", user));
        Assert.assertEquals(writerMock.getText(), "/echo Вывод введенной строчки");
    }
    @Test
    public void test2(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new About());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "about", user));
        Assert.assertEquals(writerMock.getText(), "/about Получить информацию о функционале и авторах бота");
    }
}
