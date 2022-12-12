package commands;

import domain.User;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.junit.Assert;
import org.junit.Test;
import struct.MessageInfo;

public class DeleteMailboxTest {

    @Test
    public void allCorrect() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteMailbox());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("delete_mail", new MessageInfo(0, "leonidtestoop@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Почта успешно удалена");
    }

    @Test
    public void incorrectArgs() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteMailbox());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("delete_mail", new MessageInfo(0, "qwety wasd", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }

    @Test
    public void incorrectAddress() throws MessagingException {
        var writerMock = new WriterMock();
        var bot = new Bot(writerMock);
        bot.addCommand(new DeleteMailbox());
        bot.addCommand(new Authentification());
        User user = new User((long) 0);
        bot.process("new_mail", new MessageInfo(0, "leonidtestoop@gmail.com smeghsactjibqzdo", user));
        bot.process("delete_mail", new MessageInfo(0, "bortik@gmail.com", user));
        Assert.assertEquals(writerMock.getText(), "Данная почта не найдена. Перепроверьте введенный логин");
    }
}
