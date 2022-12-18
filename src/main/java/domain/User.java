package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final Long id;
    private Set<String> favourites;
    private final HashMap<String, Mailbox> mailboxes;
    private final HashMap<String, SendLetter> writeLetters;
    private Long idDBO;

    public User(Long id) {
        this.id = id;
        mailboxes = new HashMap<>();
        writeLetters = new HashMap<>();
        favourites = new HashSet<>();
    }

    public Long getId() {
        return id;
    }
    public void setIdDBO(Long idDBO) { this.idDBO = idDBO; }
    public Long getIdDBO() { return idDBO; }
    public Set<String> getMailAdress() { return mailboxes.keySet(); }
    public Set<String> getLettersNames() { return writeLetters.keySet(); }
    public void setFavourites(Set<String> favourites) { this.favourites = favourites; }
    public Set<String> getFavourites() { return favourites; }
    public void addFavorities(String mail) { favourites.add(mail); }
    public void deleteFavorites(String mail) { favourites.remove(mail); }
    public void setMailboxes(ArrayList<Mailbox> mailboxes) { for (Mailbox mailbox : mailboxes) { addNewMailbox(mailbox); } }
    public void addNewMailbox(Mailbox mailbox) {
        mailboxes.put(mailbox.getEmail(), mailbox);
    }
    public void addNewLetter(SendLetter letter) { writeLetters.put(letter.getName(), letter); }

    public void deleteMailbox(String mail) {
        mailboxes.remove(mail);
    }
    public void deleteLetter(String name) {
        writeLetters.remove(name);
    }

    public Mailbox getMailbox(String email) {
        if (mailboxes.containsKey(email)) {
            return mailboxes.get(email);
        }
        return null;
    }
    public SendLetter getLetter(String name) {
        if (writeLetters.containsKey(name)) {
            return writeLetters.get(name);
        }
        return null;
    }
}
