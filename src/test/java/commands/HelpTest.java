package commands;

import com.pengrad.telegrambot.TelegramBot;
import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import struct.MessageInfo;

public class HelpTest {

    @Test
    public void test1() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "echo", user));
        Assert.assertEquals(writerMock.getText(), "/echo <text> Вывод введенной строчки");
    }
    @Test
    public void test2() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new About());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "about", user));
        Assert.assertEquals(writerMock.getText(), "/about Получить информацию о функционале и авторах бота");
    }
    @Test
    public void test3() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "help", user));
        Assert.assertEquals(writerMock.getText(), "/help ~command~ Получение информации о команде. При отсутствии команды после /help выводит информацию о всех командах");
    }
    @Test
    public void test4() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        bot.addCommand(new Authentification());
        bot.addCommand(new DeleteMailbox());
        bot.addCommand(new ReadLastMessages());
        bot.addCommand(new About());
        bot.addCommand(new Echo());
        User user = new User((long) 0);
        bot.process("help", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(),
                """
                        /help ~command~ Получение информации о команде. При отсутствии команды после /help выводит информацию о всех командах
                        /new_mail <address> <password> Аутентификация для дальнейшего взаимодействия почтой
                        /delete_mail <address> Удаляет данную почту
                        /read_messages <address> <number of emails being read> Чтение последних n сообщений по заданному адресу. Если на почте недостаточно сообщений - выводит все имеющиеся.
                        /about Получить информацию о функционале и авторах бота
                        /echo <text> Вывод введенной строчки
                        """);
    }
    @Test
    public void test5() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new Help());
        User user = new User((long) 0);
        bot.process("wjgnwkg", new MessageInfo(0, "", user));
        Assert.assertEquals(writerMock.getText(), "Такой команды не найдено");
    }
}
