package commands;

import domain.BotMessage;
import interfaces.IWriteRead;

public class WriterMock implements IWriteRead {
    private String output = "";
    @Override
    public void write(BotMessage message) {
        output = message.getText();
    }
    public String getText() {
        return output;
    }
}
