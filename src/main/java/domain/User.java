package domain;

import enums.UserState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final Long id;
    private Set<String> favourites;
    private final HashMap<String, Mailbox> mailboxes;

    public User(Long id) {
        this.id = id;
        mailboxes = new HashMap<>();
        favourites = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public Set<String> getMailAdress() { return mailboxes.keySet(); }
    public Set<String> getFavourites() { return favourites; }
    public void addFavorities(String mail) { favourites.add(mail); }
    public void deleteFavorites(String mail) { favourites.remove(mail); }
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
