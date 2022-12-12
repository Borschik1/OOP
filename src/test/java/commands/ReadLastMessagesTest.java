package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class ReadLastMessagesTest {

    /*@Test
    public void noNumber() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new ReadLastMessages());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("read_messages", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Некорректные параметры команды, введите /help для получения справки");
    }*/

    /*@Test
    public void correctData() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new ReadLastMessages());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("read_messages", new MessageInfo(0, "leonidtestoop@gmail.com 1", user));
        Assert.assertEquals(writerMock.getText(),
                "Sender: grihalp@gmail.com\n" +
                "Subject: Test message\n" +
                "Date: Sun Nov 27 16:11:25 YEKT 2022\n" +
                "\n" +
                "Hello, Leonidik!\n" +
                "Do you hear me?\n" +
                 "Hello, Leonidik! Do you hear me?");
    }*/
    @Test
    public void incorrectAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new ReadLastMessages());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("read_messages", new MessageInfo(0, "bortik_math@gmail.com 1", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }

    @Test
    public void noAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new ReadLastMessages());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("read_messages", new MessageInfo(0, "1", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }
}
