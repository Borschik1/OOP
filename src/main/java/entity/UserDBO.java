package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "users")

public class UserDBO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;
    private Set<String> favorites;
    private ArrayList<String> mails;
    private ArrayList<String> passwords;
    private ArrayList<Boolean> notificationFlags;

}
