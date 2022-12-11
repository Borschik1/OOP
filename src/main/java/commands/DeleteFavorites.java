package commands;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import domain.BotMessage;
import domain.User;
import enums.MessagesTemplates;
import org.example.Bot;
import struct.MessageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DeleteFavorites extends Command {
    public DeleteFavorites(){
        super("delete_favorites", "/delete_favorites <address>","Удаляет данную почту из избранного");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        User user = messageInfo.user();
        String text = messageInfo.text();
        if (messageInfo.text().equals("")) {
            Set<String> favourites = messageInfo.user().getFavourites();
            if (favourites.size() == 0) {
                bot.present(new BotMessage(MessagesTemplates.USER_FAVORITY_LIST_EMPTY.text, messageInfo.chatId()));
                return;
            }
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (String mail : favourites) {
                buttons.add(new InlineKeyboardButton(mail).callbackData("/" + getName() + " " + mail));
            }
            InlineKeyboardButton[] array = new InlineKeyboardButton[buttons.size()];
            for (int i = 0; i < buttons.size(); i++) array[i] = buttons.get(i);
            bot.present(new BotMessage(MessagesTemplates.READ_CHOOSE_MAIL.text, messageInfo.chatId(),
                    new InlineKeyboardMarkup(array)));
            return;
        }
        if (!user.getFavourites().contains(text)) {
            bot.present(new BotMessage(MessagesTemplates.MAIL_NOT_FOUND.text, messageInfo.chatId()));
            return;
        }
        user.deleteFavorites(text);
        bot.present(new BotMessage(MessagesTemplates.DELETE_FAVORITES_COMPLETE.text, messageInfo.chatId()));
    }
}
