package struct;

public class MessageInfo {

    private final long chatId;
    private final String text;
    private final String userName;

    public MessageInfo(long chatId, String text, String userName) {
        this.chatId = chatId;
        this.text = text;
        this.userName = userName;
    }


    public long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }
}
