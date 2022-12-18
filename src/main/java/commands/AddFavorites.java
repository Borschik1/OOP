package commands;

import domain.BotMessage;
import enums.MessagesTemplates;
import infrastructure.DBRepository;
import org.example.Bot;
import struct.MessageInfo;

public class AddFavorites extends Command {
    public AddFavorites(){
        super("add_favorites", "/add_favorites <mail>", "Добавить адресс в список избранных");
    }

    public void execute(MessageInfo messageInfo, Bot bot){
        String text = messageInfo.text();
        if (text.split(" ").length > 1 || text.equals("")) {
            bot.present(new BotMessage(MessagesTemplates.INCORRECT_ARGS.text, messageInfo.chatId()));
            return;
        }
        if (messageInfo.user().getFavourites().contains(text)) {
            bot.present(new BotMessage(MessagesTemplates.FAVORITES_LOGIN_ALREADY_SEEN.text, messageInfo.chatId()));
            return;
        }
        messageInfo.user().addFavorities(messageInfo.text());
        DBRepository.userFavoritesUpdate(messageInfo.user());
        bot.present(new BotMessage(MessagesTemplates.ADD_FAVORITES_COMPLETE.text, messageInfo.chatId()));
    }
}
