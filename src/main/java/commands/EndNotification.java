package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import domain.Mailbox;
import domain.User;
import enums.MessagesTemplates;
import infrastructure.DBRepository;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.example.Notification;
import struct.MessageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EndNotification extends Command{
    public EndNotification() { super("end_notification", "/end_notification <mail>",
            "Отключает уведомления от данной почты"); }

    public void execute(MessageInfo messageInfo, Bot bot) throws MessagingException {
        if (messageInfo.text().equals("")) {
            if (messageInfo.user().getFavourites() == null) {
                bot.present(new BotMessage(MessagesTemplates.USER_FAVORITY_LIST_EMPTY.text, messageInfo.chatId()));
                return;
            }
            User user = messageInfo.user();
            Set<String> mails = messageInfo.user().getMailAdress();
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (String mail : mails) {
                if (user.getMailbox(mail).getNotificationFlag()) {
                    buttons.add(new InlineKeyboardButton(mail).callbackData("/" + getName() + " " + mail));
                }
            }
            if (buttons.size() == 0) {
                bot.present(new BotMessage(MessagesTemplates.MAILBOX_NOTIFICATION_EMPTY.text, messageInfo.chatId()));
                return;
            }
            InlineKeyboardButton[] array = new InlineKeyboardButton[buttons.size()];
            for (int i = 0; i < buttons.size(); i++) array[i] = buttons.get(i);
            bot.present(new BotMessage(MessagesTemplates.READ_CHOOSE_MAIL.text, messageInfo.chatId(),
                    new InlineKeyboardMarkup(array)));
            return;
        }
        Mailbox mailbox = messageInfo.user().getMailbox(messageInfo.text());
        if (mailbox != null) {
            if (!mailbox.getNotificationFlag()) {
                bot.present(new BotMessage(MessagesTemplates.MAILBOX_NOTIFICATION_ALREADY_END.text, messageInfo.chatId()));
                return;
            }
            mailbox.setNotificationFlag(false);
            mailbox.breakNotification();
            DBRepository.userNotificationFlagUpdate(messageInfo.user());
            bot.present(new BotMessage(MessagesTemplates.DELETE_NOTIFICATION_COMPLETE.text, messageInfo.chatId()));
            return;
        }
        bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
    }
}
