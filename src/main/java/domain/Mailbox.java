package domain;

import org.example.Notification;

import java.util.TreeMap;

public class Mailbox {
    private final String email;
    private final String password;
    private boolean notificationFlag = false;
    private Notification notification;

    public Mailbox(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void breakNotification() { notification.Break(); }
    public void setNotification(Notification notification) { this.notification = notification; }

    public boolean getNotificationFlag() { return notificationFlag; }
    public void setNotificationFlag(boolean notificationFlag) {
        this.notificationFlag = notificationFlag;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}