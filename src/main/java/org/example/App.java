package org.example;

import commands.*;
import jakarta.mail.MessagingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws MessagingException {
        String token;
        try {
            Scanner scanner = new Scanner(new File("token.txt"));
            token = scanner.nextLine();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Command> commands = new ArrayList<Command>();
        commands.add(new About());
        commands.add(new Echo());
        commands.add(new Help());
        commands.add(new Authentification());
        commands.add(new DeleteMailbox());
        commands.add(new ReadLastMessages());
        commands.add(new AddFavorites());
        commands.add(new DeleteFavorites());
        commands.add(new StartNotification());
        commands.add(new EndNotification());
        TelegramBot telegramBot = new TelegramBot(token, commands);
        telegramBot.run();
    }
}
