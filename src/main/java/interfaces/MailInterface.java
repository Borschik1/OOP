package interfaces;

import domain.Mailbox;
import domain.Letter;
import domain.SendLetter;
import jakarta.mail.MessagingException;
import org.example.Notification;

public interface MailInterface {
    public Letter[] readMessages(Mailbox mailbox, int lettersCount) throws MessagingException;
    public int getMessagesCount(Mailbox mailbox) throws MessagingException;
    public Letter[] readNewMessages(Mailbox mailbox, Notification notification) throws MessagingException;

    public void sendMessage(Mailbox mailbox, SendLetter letter) throws MessagingException;

    public boolean isCredentialsCorrect(Mailbox mailbox);
}
