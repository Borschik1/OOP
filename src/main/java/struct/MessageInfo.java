package struct;

import domain.User;

public record MessageInfo(long chatId, String text, User user) {
}
