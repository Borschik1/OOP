package domain;

import enums.UserState;

import java.util.HashMap;

public class User {
    private final Long id;
    private String name;
    private final HashMap<String, Mailbox> mailboxes;

    public User(Long id) {
        this.id = id;
        this.name = "Anonim";
        mailboxes = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void addNewMailbox(Mailbox mailbox) {
        mailboxes.put(mailbox.getEmail(), mailbox);
    }

    public void deleteMailbox(String mail) {
        mailboxes.remove(mail);
    }

    public Mailbox getMailbox(String email) {
        if (mailboxes.containsKey(email)) {
            return mailboxes.get(email);
        }
        return null;
    }
}
