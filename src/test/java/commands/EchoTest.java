package commands;

import com.pengrad.telegrambot.TelegramBot;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import struct.MessageInfo;

public class EchoTest {

    @Test
    public void test1(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        bot.process("echo", new MessageInfo(0, "hello", "0"));
        Assert.assertEquals(writerMock.getText(), "hello");
    }
    @Test
    public void test2(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        bot.process("echo", new MessageInfo(0, "", "0"));
        Assert.assertEquals(writerMock.getText(), "Введите сообщение после команды");
    }
    @Test
    public void test3(){
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Echo());
        bot.process("wjgnwkg", new MessageInfo(0, "", "0"));
        Assert.assertEquals(writerMock.getText(), "Такой команды не найдено");
    }
}
