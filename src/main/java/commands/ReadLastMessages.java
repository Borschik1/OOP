package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import domain.Letter;
import domain.Mailbox;
import jakarta.mail.MessagingException;
import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReadLastMessages extends Command {

    public ReadLastMessages() {
        super("read_messages", "/read_messages <address> <number of emails being read>", "Чтение последних n сообщений по заданному адресу. Если на почте недостаточно сообщений - выводит все имеющиеся.");
    }

    public void execute(MessageInfo messageInfo, Bot bot) throws MessagingException {
        String[] args = messageInfo.text().split("\s");
        if (messageInfo.text().equals("")) {
            Set<String> mails = messageInfo.user().getMailAdress();
            if (mails.size() == 0) {
                bot.present(new BotMessage(MessagesTemplates.USER_MAIL_LIST_EMPTY.text, messageInfo.chatId()));
                return;
            }
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (String mail : mails) {
                buttons.add(new InlineKeyboardButton(mail).callbackData("/" + getName() + " " + mail));
            }
            InlineKeyboardButton[] array = new InlineKeyboardButton[buttons.size()];
            for (int i = 0; i < buttons.size(); i++) array[i] = buttons.get(i);
            bot.present(new BotMessage(MessagesTemplates.READ_CHOOSE_MAIL.text, messageInfo.chatId(),
                    new InlineKeyboardMarkup(array)));
            return;
        }
        if (args.length == 1) {
            if (messageInfo.user().getMailbox(messageInfo.text()) == null) {
                bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
                return;
            }
            bot.present(new BotMessage(MessagesTemplates.READ_CHOOSE_LETTERS_NUMBER.text, messageInfo.chatId(),
                    new InlineKeyboardMarkup(
                            new InlineKeyboardButton("1").callbackData("/" + getName() + " " + args[0] + " 1"),
                            new InlineKeyboardButton("3").callbackData("/" + getName() + " " + args[0] + " 3"),
                            new InlineKeyboardButton("5").callbackData("/" + getName() + " " + args[0] + " 5"))));
            return;
        }
        User user = messageInfo.user();
        if (user.getMailbox(args[0]) != null) {
            Letter[] letters = bot.mailInterface.readMessages(user.getMailbox(args[0]), Integer.parseInt(args[1]));
            if (letters == null) {
                bot.present(new BotMessage(MessagesTemplates.EMPTY_MAIL.text, messageInfo.chatId()));
                return;
            }
            for (Letter letter : letters) {
                bot.present(new BotMessage(letter.toString(), messageInfo.chatId()));
                System.out.println(letter);
            }
            return;
        }
        bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
    }
}
