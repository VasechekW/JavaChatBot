package main.java.chatbot.io;

import java.util.Scanner;

public class ConsoleInput implements UserInput {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine(){
        return scanner.nextLine();
    }

    public void close(){
        scanner.close();
    }
}
