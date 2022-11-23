package interfaces;

import domain.Mailbox;
import domain.Letter;
import jakarta.mail.MessagingException;

public interface MailInterface {
    public Letter[] readMessages(Mailbox mailbox, int lettersCount) throws MessagingException;

    public void sendMessage(Mailbox mailbox, Letter letter);

    public boolean isCredentialsCorrect(Mailbox mailbox);
}
