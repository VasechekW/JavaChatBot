package main.java.chatbot;

import main.java.chatbot.ui.ChatBot;
import main.java.chatbot.io.ConsoleInput;
import main.java.chatbot.core.DataBaseBot;
import main.java.chatbot.ui.MatrixDialogue;
import main.java.chatbot.io.ConsoleOutput;

public class Main{

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        DataBaseBot bot = new DataBaseBot();
        MatrixDialogue matrixDialogue = new MatrixDialogue(input, output);
        ChatBot chatBot = new ChatBot(input, output, bot, matrixDialogue);

        try {
            chatBot.run();
        } finally {
            input.close();
        }
    }
}