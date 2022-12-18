package infrastructure;

import com.sun.mail.imap.IMAPFolder;
import domain.SendLetter;
import jakarta.mail.*;
import domain.Letter;
import domain.Mailbox;
import interfaces.MailInterface;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.Notification;

import java.util.Objects;
import java.util.Properties;
import java.util.Set;

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
        if (notification.getLastReadMessage() == totalMessages) { return null; }
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
    public void sendMessage(Mailbox mailbox, SendLetter letter) throws MessagingException {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailbox.getEmail(), mailbox.getPassword()); }
        });
        Transport.send(Objects.requireNonNull(prepareMessage(session, letter)));
    }

    private static Message prepareMessage(Session session, SendLetter letter) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(letter.getFrom().getEmail()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(letter.getTo()));
            message.setSubject(letter.getSubject());
            message.setText(letter.getText());
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
