package domain;

import java.util.HashMap;

public class UserList {
    private HashMap<Long, User> userList;

    public UserList() {
        userList = new HashMap<>();
    }

    public void addUser(User user) {
        Long id = user.getId();
        userList.put(id, user);
    }

    public User getUserById(Long id) {
        if (!userList.containsKey(id)) {
            userList.put(id, new User(id));
        }
        return userList.get(id);
    }
}
