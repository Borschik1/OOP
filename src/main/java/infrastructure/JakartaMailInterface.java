package infrastructure;

import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.*;
import domain.Letter;
import domain.Mailbox;
import interfaces.MailInterface;
import org.example.Notification;

import java.util.Properties;

public class JakartaMailInterface implements MailInterface {
    static private Properties getProperties(String email) {
        Properties properties = new Properties();

        properties.setProperty("mail.store.protocol", "imap");
        properties.setProperty("mail.imap.ssl.enable", "true");

        switch (email.split("@")[1]) {
            case "gmail.com" -> {
                properties.setProperty("mail.imap.host", "imap.gmail.com");
                properties.setProperty("mail.imap.port", "993");
            }
            case "yandex.ru" -> {
                properties.setProperty("mail.imap.host", "imap.yandex.ru");
                properties.setProperty("mail.imap.port", "993");
            }
            default -> throw new IllegalArgumentException("Unknown email domain");
        }

        return properties;
    }

    @Override
    public Letter[] readMessages(Mailbox mailbox, int lettersCount)
            throws MessagingException {
        String email = mailbox.getEmail();
        String password = mailbox.getPassword();

        Properties props = getProperties(email);

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imap");
        store.connect(props.getProperty("mail.imap.host"), email, password);
        IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        int totalMessages = inbox.getMessageCount();
        if (totalMessages == 0) { return null; }
        Message[] mailMessages = inbox.getMessages(Math.max(1, totalMessages - lettersCount + 1), totalMessages);

        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(IMAPFolder.FetchProfileItem.MESSAGE);

        inbox.fetch(mailMessages, fp);

        Letter[] letters = new Letter[mailMessages.length];
        for (int i = 0; i < mailMessages.length; i++) {
            letters[i] = Letter.fromMailMessage(mailMessages[i]);
        }
        return letters;
    }
    @Override
    public int getMessagesCount(Mailbox mailbox) throws MessagingException {
        String email = mailbox.getEmail();
        String password = mailbox.getPassword();

        Properties props = getProperties(email);

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imap");
        store.connect(props.getProperty("mail.imap.host"), email, password);
        IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        return inbox.getMessageCount();
    }
    @Override
    public Letter[] readNewMessages(Mailbox mailbox, Notification notification) throws MessagingException {
        String email = mailbox.getEmail();
        String password = mailbox.getPassword();

        Properties props = getProperties(email);

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imap");
        store.connect(props.getProperty("mail.imap.host"), email, password);
        IMAPFolder inbox = (IMAPFolder) store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        int totalMessages = inbox.getMessageCount();
        if (totalMessages == 0) { return null; }
        if (notification.getLastReadMessage() == totalMessages) return null;
        Message[] mailMessages = inbox.getMessages(notification.getLastReadMessage(), totalMessages);
        notification.setLastReadMessage(totalMessages);

        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(IMAPFolder.FetchProfileItem.MESSAGE);

        inbox.fetch(mailMessages, fp);

        Letter[] letters = new Letter[mailMessages.length];
        for (int i = 0; i < mailMessages.length; i++) {
            letters[i] = Letter.fromMailMessage(mailMessages[i]);
        }
        return letters;
    }

    @Override
    public void sendMessage(Mailbox mailbox, Letter letter) {

    }

    @Override
    public boolean isCredentialsCorrect(Mailbox mailbox){
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "imaps");
            properties.setProperty("mail.imaps.ssl.enable", "true");
            properties.setProperty("mail.imaps.host", "imap.gmail.com");
            properties.setProperty("mail.imaps.port", "993");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailbox.getEmail(), mailbox.getPassword());
                }
            });
            session.getStore().connect();
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
