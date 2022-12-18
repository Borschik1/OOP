package infrastructure;

import domain.BotMessage;
import domain.Mailbox;
import domain.User;
import entity.UserDBO;
import jakarta.mail.MessagingException;
import org.example.Bot;
import org.example.Notification;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBRepository {
    private static final DBInterface dbInterface = new DBInterface();

    public static void userAdd(User user) {
        UserDBO userDBO = new UserDBO();
        user.setIdDBO(userDBO.getId());
        userDBO.setUserId(user.getId());
        userDBO.setFavorites(user.getFavourites());
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();
        for (String mail : user.getMailAdress()) {
            mails.add(mail);
            passwords.add(user.getMailbox(mail).getPassword());
        }
        userDBO.setMails(mails);
        userDBO.setPasswords(passwords);
        dbInterface.saveUserDB(userDBO);
    }

    public static void userFavoritesUpdate(User user) {
        UserDBO userDBO = dbInterface.findById(user.getIdDBO());
        userDBO.setFavorites(user.getFavourites());
        dbInterface.updateUserDB(userDBO);
    }

    public static void userMailUpdate(User user) {
        UserDBO userDBO = dbInterface.findById(user.getIdDBO());
        ArrayList<String> mails = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();
        for (String mail : user.getMailAdress()) {
            mails.add(mail);
            passwords.add(user.getMailbox(mail).getPassword());
        }
        userDBO.setMails(mails);
        userDBO.setPasswords(passwords);
        dbInterface.updateUserDB(userDBO);
    }

    public static void userNotificationFlagUpdate(User user) {
        UserDBO userDBO = dbInterface.findById(user.getIdDBO());
        ArrayList<Boolean> notificationFlags = new ArrayList<>();
        for (String mail : user.getMailAdress()) {
            notificationFlags.add(user.getMailbox(mail).getNotificationFlag());
        }
        userDBO.setNotificationFlags(notificationFlags);
        dbInterface.updateUserDB(userDBO);
    }

    public static void createUsersFromDB(List<UserDBO> users, Bot bot) throws MessagingException {
        for (UserDBO userDBO : users) {
            User user = new User(userDBO.getUserId());
            user.setFavourites(userDBO.getFavorites());
            ArrayList<String> mails = userDBO.getMails();
            ArrayList<String> passwords = userDBO.getPasswords();
            ArrayList<Boolean> notificationFlags = userDBO.getNotificationFlags();
            ArrayList<Mailbox> mailboxes = new ArrayList<>();
            for (int i = 0; i < mails.size(); ++i) {
                Mailbox mailbox = new Mailbox(mails.get(i), passwords.get(i));
                if (notificationFlags.get(i)) {
                    mailbox.setNotificationFlag(true);
                    Notification notification = new Notification(user, mailbox, bot);
                    mailbox.setNotification(notification);
                    Thread threadNotification = new Thread(notification);
                    threadNotification.start();
                    DBRepository.userNotificationFlagUpdate(user);
                }
                mailboxes.add(mailbox);
            }
            user.setMailboxes(mailboxes);
            bot.userList.addUser(user);
        }
    }
}
