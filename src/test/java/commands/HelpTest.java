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
    @Test
    public void test3(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.process("help", new MessageInfo(0, "help", "0"));
        Assert.assertEquals(writerMock.getText(), "/help Получение информации о команде. Если после /help стоит название команды, то выводится ее описание");
    }
    @Test
    public void test4(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new Echo());
        bot.addCommand(new About());
        bot.process("help", new MessageInfo(0, "", "0"));
        Assert.assertEquals(writerMock.getText(),
                        "/help Получение информации о команде. Если после /help стоит название команды, то выводится ее описание\n" +
                        "/about Получить информацию о функционале и авторах бота\n" +
                        "/echo Вывод введенной строчки\n");
    }
    @Test
    public void test5(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.process("wjgnwkg", new MessageInfo(0, "", "0"));
        Assert.assertEquals(writerMock.getText(), "Такой команды не найдено");
    }
}
