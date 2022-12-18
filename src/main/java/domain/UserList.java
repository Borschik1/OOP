package domain;

import infrastructure.DBRepository;

import java.util.HashMap;

public class UserList {
    private HashMap<Long, User> userList;

    public UserList() {
        userList = new HashMap<>();
    }

    public void addUser(User user) { userList.put(user.getId(), user); DBRepository.userAdd(user); }

    public User getUserById(Long id) {
        if (!userList.containsKey(id)) {
            User user = new User(id);
            addUser(user);
        }
        return userList.get(id);
    }
}
