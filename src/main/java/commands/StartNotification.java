package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import domain.Mailbox;
import enums.MessagesTemplates;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.example.Notification;
import struct.MessageInfo;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StartNotification extends Command{
    public StartNotification() { super("start_notification", "/start_notification <mail>",
            "Запускает автоматическое чтение новых сообщений пришедших от избранных адресов"); }

    public void execute(MessageInfo messageInfo, Bot bot) throws MessagingException {
        String[] args = messageInfo.text().split("\s");
        if (messageInfo.text().equals("")) {
            if (messageInfo.user().getFavourites() == null) {
                bot.present(new BotMessage(MessagesTemplates.USER_FAVORITY_LIST_EMPTY.text, messageInfo.chatId()));
                return;
            }
            Set<String> mails = messageInfo.user().getMailAdress();
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
        Mailbox mailbox = messageInfo.user().getMailbox(messageInfo.text());
        if (mailbox != null) {
            if (mailbox.getNotificationFlag()) {
                bot.present(new BotMessage(MessagesTemplates.MAILBOX_NOTIFICATION_ALREADY_START.text, messageInfo.chatId()));
                return;
            }
            mailbox.setNotificationFlag(true);
            Notification notification = new Notification(messageInfo.user(), mailbox, messageInfo.chatId(), bot);
            mailbox.setNotification(notification);
            Thread threadNotification = new Thread(notification);
            threadNotification.start();
            bot.present(new BotMessage(MessagesTemplates.MAILBOX_NOTIFICATION_COMPLETE.text, messageInfo.chatId()));
            return;
        }
        bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
    }
}
