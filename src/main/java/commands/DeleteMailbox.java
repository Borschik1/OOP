package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import infrastructure.DBRepository;
import org.example.Bot;
import struct.MessageInfo;
import enums.MessagesTemplates;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DeleteMailbox extends Command {

    public DeleteMailbox(){
        super("delete_mail", "/delete_mail <address>","Удаляет данную почту");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        User user = messageInfo.user();
        String text = messageInfo.text();
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
        if (user.getMailbox(text) != null) {
            user.deleteMailbox(text);
            DBRepository.userMailUpdate(messageInfo.user());
            bot.present(new BotMessage(MessagesTemplates.DELETE_COMPLETE.text, messageInfo.chatId()));
            return;
        }
        bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
    }
}
