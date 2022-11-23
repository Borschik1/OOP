package commands;

import domain.Letter;
import domain.Mailbox;
import jakarta.mail.MessagingException;
import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

public class ReadLastMessages extends Command {

    public ReadLastMessages(){
        super("read_messages", "Чтение последних n сообщений по заданному адресу. Если на почте недостаточно сообщений - выводит все имеющиеся.");
    }

    public void execute(MessageInfo messageInfo, Bot bot) throws MessagingException {
        String[] args = messageInfo.text().split("\s");
        if (args.length != 2) {
            bot.present(messageInfo.chatId(), MessagesTemplates.INCORRECT_ARGS.text);
            return;
        }
        User user = messageInfo.user();
        if (user.getMailbox(args[0]) == null) {
            Letter[] letters = bot.mailInterface.readMessages(user.getMailbox(args[0]), Integer.parseInt(args[1]));
            for (Letter letter : letters) { bot.present(messageInfo.chatId(), letter.toString()); }
            return;
        }
        bot.present(messageInfo.chatId(), MessagesTemplates.MAIL_NOT_FOUND.text);
    }
}
