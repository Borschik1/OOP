package domain;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public class BotMessage {
    private String text = null;
    private final Long chatId;
    private InlineKeyboardMarkup buttons;


    public String getText() {
        return text;
    }

    public Long getChatId() {
        return chatId;
    }
    public InlineKeyboardMarkup getButtons() {
        return buttons;
    }

    /*public InlineKeyboardMarkup getMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        if (buttons != null) {
            for (InlineKeyboardButton button : buttons) {
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                rowInline.add(button);
                rowsInline.add(rowInline);
            }
        }

        inlineKeyboardMarkup.setKeyboard(rowsInline);
        return inlineKeyboardMarkup;
    }*/

    public BotMessage(String text, Long chatId) {
        this.text = text;
        this.chatId = chatId;
    }

    public BotMessage(String text, Long chatId, InlineKeyboardMarkup buttons) {
        this.text = text;
        this.chatId = chatId;
        this.buttons = buttons;
    }

    public BotMessage(Long chatId, InlineKeyboardMarkup buttons) {
        this.chatId = chatId;
        this.buttons = buttons;
    }
}