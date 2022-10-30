package commands;

import com.pengrad.telegrambot.TelegramBot;
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
        bot.process("help", new MessageInfo(0, "echo", "0"));
        Assert.assertEquals(writerMock.getText(), "/echo Вывод введенной строчки");
    }
    @Test
    public void test2(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new About());
        bot.process("help", new MessageInfo(0, "about", "0"));
        Assert.assertEquals(writerMock.getText(), "/about Получить информацию о функционале и авторах бота");
    }
}
