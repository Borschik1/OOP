package commands;

import org.example.IWriteRead;

public class WriterMock implements IWriteRead {
    private String output = "";
    @Override
    public void write(long chatId, String text) {
        output = text;
    }
    public String getText() {
        return output;
    }
}
