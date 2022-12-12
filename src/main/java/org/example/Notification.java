package org.example;

import domain.BotMessage;
import domain.Letter;
import domain.Mailbox;
import domain.User;
import jakarta.mail.MessagingException;

import java.util.NoSuchElementException;

public class Notification implements Runnable {
    private User user;
    private Long chatId;
    private Mailbox mailbox;
    private final Bot bot;
    private boolean runable = true;
    private int lastReadMessage;

    public Notification(User user, Mailbox mailbox, Long chatId, Bot bot) throws MessagingException {
        this.bot = bot;
        this.user = user;
        this.mailbox = mailbox;
        this.chatId = chatId;
        lastReadMessage = bot.mailInterface.getMessagesCount(mailbox);
    }

    public void setLastReadMessage(int lastReadMessage) { this.lastReadMessage = lastReadMessage; }
    public int getLastReadMessage() { return lastReadMessage; }

    public void notificationBreak() { runable = false; }

    public void run() {
        while (runable) {
            try {
                Letter[] letters = bot.mailInterface.readNewMessages(mailbox, this);
                if (letters != null) {
                    for (Letter letter : letters) {
                        if (user.getFavourites().contains(letter.getSender())) {
                            bot.present(new BotMessage(letter.toString(), chatId));
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                continue;
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
